# AGENTS: How to work productively in this repo

Quick summary (what matters most)
- This repo contains two UI projects and a multi-module Java backend (RuoYi):
  - Backend: `RuoYi-Vue` (Maven multi-module, modules: `ruoyi-admin`, `ruoyi-framework`, `ruoyi-system`, `ruoyi-common`, `ruoyi-quartz`, `ruoyi-generator`). See `RuoYi-Vue/pom.xml`.
  - Frontend (Vue 2): `RuoYi-Vue/ruoyi-ui` (Vue CLI, `npm run dev` / `npm run build:prod`). See `ruoyi-ui/package.json`.
  - Frontend (Vue 3 + Vite): `RuoYi-Vue3` (Vite, `npm run dev` / `npm run build:prod`). See `RuoYi-Vue3/package.json`.

Architecture & important cross-cutting concerns
- The Java backend is a Maven aggregator (top-level `pom.xml`) that builds several internal modules. `ruoyi-admin` is the web service entrypoint (Spring Boot).
- Authentication: JWT tokens + Redis session cache. Key classes:
  - `ruoyi-framework/src/main/java/.../web/service/TokenService.java` (creates/parses JWT; requires properties `token.header`, `token.secret`, `token.expireTime`).
  - `ruoyi-common/src/main/java/.../core/redis/RedisCache.java` (Redis helper used across services).
- APIs the frontends call (examples):
  - POST `/login` — implemented in `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/SysLoginController.java`
  - GET `/getInfo` and GET `/getRouters` — used by the UI to obtain user/route/menu data.
- Data layer: MyBatis + MySQL. DB seed and schema scripts live in `RuoYi-Vue/sql/` (useful when setting up local DB or reproducing issues).

Developer workflows (concrete commands)
- Build the whole Java project (from repo root):
  - mvn -T 1C clean package -DskipTests
  - or build & run only the server module: cd `RuoYi-Vue/ruoyi-admin` && mvn spring-boot:run
- Run the packaged backend jar (artifact generated at `RuoYi-Vue/ruoyi-admin/target/`):
  - java -jar RuoYi-Vue/ruoyi-admin/target/ruoyi-admin.jar
  - Windows helper: `RuoYi-Vue/bin/run.bat` shows example JVM options and jar invocation.
- Frontend (Vue 2) dev / build:
  - cd RuoYi-Vue/ruoyi-ui && npm install
  - npm run dev   # local dev server (vue-cli-service)
  - npm run build:prod  # production bundle
- Frontend (Vue 3 + Vite):
  - cd RuoYi-Vue3 && npm install
  - npm run dev
  - npm run build:prod

Project-specific conventions & patterns agents must know
- Multi-module Maven: Top-level POM is packaging `pom` with modules declared. Edit or add Java code in the module containing the artifact (e.g., change controllers in `ruoyi-admin`). Use `-pl` and `-am` options when you want to build a subset.
- Auth/session handling is centralized in `TokenService` + `RedisCache`. Changes to token format or TTL must touch both token generation/parsing and any clients that expect `Constants.TOKEN` header.
- The frontends expect small REST surface names (e.g. `/login`, `/getRouters`); UI code constructs menus from backend JSON — search the frontends for `getRouters` to see mapping conventions.
- Code generation & dynamic code: `ruoyi-generator` module is present and used by the project to scaffold CRUD pages — be cautious when changing generated code patterns: generator templates live under `ruoyi-generator`.

Integration points & runtime requirements
- External services required to run the system locally (not checked in):
  - MySQL (schema/data in `RuoYi-Vue/sql/*.sql`), Redis (used heavily for tokens and caches).
  - Properties like datasource URL/user/password and JWT secrets are read from Spring properties (not committed). Look for usages of `@Value` (e.g. `TokenService`) to discover required keys.
- Runtime/test evidence in repo: `java-start-test.log` (shows Java 17), `npm-start-test.log` (shows Node 11.11). Do not assume a single Node version across projects; Vite requires newer Node than old Vue-CLI.

Where to look first when changing behavior
- Authentication & session lifetime: `TokenService.java`, `RedisCache.java`, `Constants` files under `ruoyi-common`.
- API surface & controllers: `ruoyi-admin/src/main/java/com/ruoyi/web/controller/**` (login, menus, monitoring controllers). Example: `SysLoginController.java`, `SysMenuController.java`.
- Frontend integration: `RuoYi-Vue/ruoyi-ui/src` (Vue 2) and `RuoYi-Vue3/src` (Vue 3). Check `package.json` scripts for how dev and prod builds are invoked.

Testing & debugging tips
- Hot reload for backend: `spring-boot-devtools` is included in `ruoyi-admin` dependencies — run with IDE or `mvn spring-boot:run` to get reload behavior on classpath changes.
- Logs and runtime artifacts: `ruoyi-admin/target/` contains the packaged jar; repository root contains several log files created by earlier runs — check `*.out.log` and `*.err.log` for past runtime traces.

Quick file map (start here)
- `RuoYi-Vue/pom.xml` — aggregator pom
- `RuoYi-Vue/ruoyi-admin/` — backend entrypoint (pom, controllers)
- `RuoYi-Vue/ruoyi-common/` — shared utils (RedisCache, constants)
- `RuoYi-Vue/ruoyi-framework/` — web service helpers (TokenService)
- `RuoYi-Vue/ruoyi-ui/package.json` — Vue2 frontend scripts
- `RuoYi-Vue3/package.json` — Vue3 Vite frontend scripts
- `RuoYi-Vue/sql/*.sql` — DB schema and seed data

If you are an automated agent making edits
- Prefer making minimal, module-scoped changes and run `mvn -pl <module> -am package` to validate compile.
- When changing authentication or token handling, also update frontend code that reads/stores `Constants.TOKEN` header.
- Add or update SQL schema changes under `RuoYi-Vue/sql/` and keep migrations reproducible.

References
- Examples mentioned above: `TokenService.java`, `RedisCache.java`, `SysLoginController.java`, `ruoyi-ui/package.json`, `RuoYi-Vue3/package.json`, `RuoYi-Vue/bin/run.bat`.

必填配置键 & 环境变量（快速列表）
- `token.header` — HTTP 头名，TokenService 使用此头读取令牌（常见值：`Authorization` 或 `token`）。
- `token.secret` — JWT 签名密钥（必须与运行环境一致）。
- `token.expireTime` — 令牌过期时间（以分钟计）。
- 数据库相关：`spring.datasource.*`（url/user/password 等）。
- Redis 相关：`spring.redis.*`（host/port/password）。

示例：使用 `curl` 完成登录并调用受保护接口（示例假定服务在 `http://localhost:8080` 运行）
1) 登录并从响应中提取 `token` 字段（返回 JSON 包含 `"token": "..."`）

```powershell
# 登录（JSON body 要包含 LoginBody 所需字段：username,password,code,uuid）
curl -s -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username":"admin","password":"admin123","code":"","uuid":""}' > login.json
# 使用 jq 提取 token（如果没有 jq，请手动查看 login.json）
cat login.json | ConvertFrom-Json | Select-Object -ExpandProperty token
```

2) 使用提取到的 token 调用受保护接口（TokenService 根据 `token.header` 配置读取头并期望前缀 `Bearer `）

```powershell
$token = (Get-Content login.json | ConvertFrom-Json).token
# 假定 token.header=Authorization（常见情况）
curl -s http://localhost:8080/getRouters -H "Authorization: Bearer $token"
# 或者如果项目配置 token.header=token：
curl -s http://localhost:8080/getRouters -H "token: Bearer $token"
```

PR 校验清单（在触及认证、数据库或前后端契约改变时使用）
- 如果变更认证/token 行为：
  - 搜索并更新所有读取 token 的代码（查找 `Constants.TOKEN`、`Constants.TOKEN_PREFIX`、`token.header` 的使用）。
  - 确保前端在 `ruoyi-ui` 或 `RuoYi-Vue3` 中也相应更新（搜索 `getToken`、`token` 或 `Authorization`）。
  - 手动在本地运行后端和前端，完成一次登录流程并调用 `/getRouters` 验证菜单加载正常。
- 如果变更数据库/模式：
  - 将 SQL 变更写入 `RuoYi-Vue/sql/` 并保留可重放的语句（不要修改生产数据直接的不可逆脚本）。
  - 本地验证：导入 SQL、运行 `mvn -pl ruoyi-admin -am package` 并启动后端，检查数据访问层是否报错。
- 一般验证命令：
  - 编译后端模块（快速）：
    ```powershell
    mvn -pl ruoyi-admin -am package -DskipTests
    ```
  - 启动前端进行快速手工验收：
    ```powershell
    cd RuoYi-Vue/ruoyi-ui; npm install; npm run dev
    # 或
    cd RuoYi-Vue3; npm install; npm run dev
    ```

Agent 提交建议
- 提交说明中列出影响范围（后端模块名、受影响 API、是否需要前端修改、是否需要 DB 脚本）。
- 在 PR 描述内贴入一段「手工验证步骤」：如何登录、如何复现变更点、预期行为与实际输出示例（例如登录返回的 token 样例、/getRouters 返回的 JSON 片段）。
