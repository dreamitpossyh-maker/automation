# 学习培训工作台首页开发文档

## 1. 文档说明

本文档基于效果图 `D:/AppGallery/App/wechatFiles/xwechat_files/wxid_aq2ctg6918gz22_75d5/temp/RWTemp/2026-05/7f30e2e4dca9746f89547e79156a2475.png` 进行页面分析与开发拆解，适用于当前 `RuoYi-Vue3` 前端项目的首页/工作台模块开发。

由于目前只有静态效果图，文档中的接口路径、字段命名和部分业务状态为实现建议，实际落地时可按后端现有模块命名调整。

## 2. 页面定位

页面类型：学习培训工作台首页。

核心目标：

- 给登录用户展示个人学习概览、年度学习趋势和快捷入口。
- 聚合待办流程、线下培训通知、线上培训通知、在线考试通知。
- 支持用户从首页直接进入学习、考试、流程办理、课程库、我的课程、证书、成绩等业务页面。

目标用户：

- 普通学员：查看学习任务、培训通知、考试通知并进入学习/考试。
- 培训管理员或部门负责人：查看通知、待办审批和统计概况。

## 3. 效果图结构分析

整体为后台管理类仪表盘布局，白色内容卡片铺在浅灰背景上。页面没有明显营销性质，重点是信息扫描、任务处理和入口跳转。

页面从上到下分为三行：

| 区域 | 模块 | 说明 |
| --- | --- | --- |
| 第一行左侧 | 用户学习概览卡片 | 展示头像、姓名、工号、密级，以及本年度培训/学习/总学习/总培训时长 |
| 第一行中间 | 年度趋势折线图 | 展示 2026 年度学习时长、培训时长按月趋势 |
| 第一行右侧 | 快捷入口 | 课程库、我的课程、我的证书、考试成绩，部分入口带角标 |
| 第二行左侧 | 我的待办流程 | 审批、申请、认定、资格初审等流程待办 |
| 第二行右侧 | 线下培训通知 | 线下培训类通知列表 |
| 第三行左侧 | 线上培训通知 | 线上课程/培训项目列表，含起止时间、负责人、状态、行动按钮 |
| 第三行右侧 | 在线考试通知 | 考试列表，含考试时间、负责人、公开状态、开始考试按钮 |

效果图中红框重点标注了：

- 快捷入口区：需要作为独立组件开发，图标、角标、跳转规则要清晰。
- 三类通知标题：`线下培训通知`、`线上培训通知`、`在线考试通知`，说明这三块是首页的核心列表模块。

## 4. 视觉规范

### 4.1 页面布局

建议页面根节点：

- 背景色：`#f5f7fb`
- 页面内边距：`16px`
- 卡片间距：`16px`
- 最大宽度：跟随若依后台内容区，不额外限制；在宽屏下横向铺满。

桌面端布局建议：

```text
┌──────────────┬──────────────┬──────────────┐
│ 用户概览      │ 年度趋势      │ 快捷入口      │
├──────────────┴──────────────┬──────────────┤
│ 我的待办流程                 │ 线下培训通知   │
├─────────────────────────────┼──────────────┤
│ 线上培训通知                 │ 在线考试通知   │
└─────────────────────────────┴──────────────┘
```

推荐 CSS Grid：

- 第一行：`grid-template-columns: repeat(3, minmax(0, 1fr))`
- 第二、三行：`grid-template-columns: repeat(2, minmax(0, 1fr))`
- 小屏幕小于 `1200px`：第一行可变成 `2 + 1` 或单列。
- 小屏幕小于 `768px`：所有模块单列堆叠。

### 4.2 卡片样式

通用卡片：

- 背景：`#ffffff`
- 圆角：`8px`
- 边框：`1px solid #edf0f5`
- 阴影：`0 4px 14px rgba(31, 45, 61, 0.04)`
- 内边距：`20px 24px`
- 标题字号：`16px`
- 标题字重：`600`
- 正文字号：`14px`
- 辅助文字字号：`12px`

列表行：

- 高度：建议 `56px` 到 `68px`
- 下边框：`1px solid #f0f2f5`
- 图标区宽度：`28px`
- 内容区自适应，标题单行省略，副标题最多一行省略。

### 4.3 色彩

| 用途 | 色值 | 说明 |
| --- | --- | --- |
| 页面背景 | `#f5f7fb` | 后台浅灰背景 |
| 卡片背景 | `#ffffff` | 主要内容容器 |
| 主文字 | `#1f2d3d` | 标题/重要内容 |
| 次文字 | `#7a8798` | 人员、时间、说明 |
| 弱文字 | `#a8b2c1` | 标签、图标、辅助信息 |
| 主蓝 | `#3b82f6` | 学习时长、公开标签、入口图标 |
| 绿色 | `#21b883` | 学习时长趋势、开始时间、正常状态 |
| 橙色 | `#f59e0b` | 证书/重点提示 |
| 红色 | `#ef4444` | 截止时间、角标 |
| 紫色 | `#8b5cf6` | 我的课程、成绩入口 |

## 5. 模块拆解

### 5.1 用户学习概览卡片

组件建议：`UserLearningSummaryCard`

展示内容：

- 用户头像：方形圆角头像，若无头像则显示姓氏/首字。
- 用户姓名：如 `张华强`。
- 用户标签：如 `在职`。
- 工号：如 `ST20240982`。
- 密级：如 `二级空域`，使用橙色强调。
- 四个统计项：
  - 本年度培训：`128h`
  - 本年度学习：`256h`
  - 总学习时：`1240h`
  - 总培训时：`860h`

交互：

- 点击头像或姓名进入个人中心。
- 统计项暂不需要点击；若后续有明细页，可跳转学习记录/培训记录。

### 5.2 年度趋势折线图

组件建议：`LearningTrendChart`

技术建议：使用项目已有 `echarts`。

图表内容：

- 标题：`2026年度学习统计趋势`
- X 轴：`1月` 到 `12月`
- Y 轴：时长，单位小时，可隐藏单位但 tooltip 需展示。
- 两条折线：
  - `学习时长`：绿色
  - `培训时长`：蓝色

交互：

- 鼠标悬停展示 tooltip：月份、学习时长、培训时长。
- 图例可点击切换显示。
- 图表容器尺寸变化时调用 `resize()`。

### 5.3 快捷入口

组件建议：`QuickEntryPanel`

入口列表：

| 名称 | 图标建议 | 主色 | 角标 | 跳转 |
| --- | --- | --- | --- | --- |
| 课程库 | `Reading` | 蓝色 | 无 | `/training/course-library` |
| 我的课程 | `School` | 紫色 | `12` | `/training/my-courses` |
| 我的证书 | `Medal` | 橙色 | `6` | `/training/my-certificates` |
| 考试成绩 | `Document` | 紫色 | 无 | `/training/exam-results` |

交互：

- 整个入口项可点击，鼠标悬停轻微上浮或背景变浅。
- 角标显示未完成、待领取、待查看等数量。
- 数量为 `0` 时不显示角标。

### 5.4 我的待办流程

组件建议：`TodoProcessList`

展示内容：

- 标题：`我的待办流程`
- 右侧：`查看全部`
- 列表项包含：
  - 流程类型图标
  - 待办标题
  - 公开状态标签
  - 发起人/负责人
  - 操作按钮：`办理 >`

示例数据：

- `课程审批：Python数据分析进阶`
- `考试补考申请：安全生产规范`
- `培训学时认定：人工智能研讨会`
- `讲师入库资格初审`

交互：

- 点击 `办理` 进入流程详情页。
- 点击 `查看全部` 进入待办中心。
- 列表为空时展示空状态：`暂无待办流程`。

### 5.5 线下培训通知

组件建议：`OfflineTrainingNoticeList`

展示内容：

- 标题：`线下培训通知`
- 右侧：`查看全部`
- 列表项包含：
  - 通知图标
  - 通知标题
  - 所属部门/来源
  - 发布日期
  - 发布人
  - 操作按钮：`办理 >` 或 `查看 >`

示例数据：

- `关于2026年第二季度线上课程更新的通知`
- `关于举行《数字化转型白皮书》解读会的通知`
- `您有一张证书即将过期，请及时参加换证培训`
- `5月份“名师讲堂”排课计划公布`

### 5.6 线上培训通知

组件建议：`OnlineTrainingList`

展示内容：

- 标题：`线上培训通知`
- 右侧：`查看全部`
- 列表项包含：
  - 培训图标
  - 培训名称
  - 培训简介
  - 起止时间
  - 负责人
  - 学习状态标签
  - 公开标签
  - 操作按钮：`继续学习 >` 或 `开始学习 >`

示例状态：

| 状态 | 文案 | 样式 |
| --- | --- | --- |
| `not_started` | 未学习 | 灰色 |
| `learning` | 学习中 | 深色/蓝色 |
| `completed` | 已完成 | 绿色 |
| `expired` | 已结束 | 灰色禁用 |

### 5.7 在线考试通知

组件建议：`OnlineExamList`

展示内容：

- 标题：`在线考试通知`
- 右侧：`查看全部`
- 列表项包含：
  - 考试图标
  - 考试名称
  - 考试说明/分类
  - 起止时间
  - 负责人
  - 公开标签
  - 操作按钮：`开始考试 >`

按钮规则：

| 时间状态 | 按钮文案 | 是否可点 |
| --- | --- | --- |
| 未开始 | `未开始` | 否 |
| 进行中 | `开始考试 >` | 是 |
| 已提交 | `查看成绩 >` | 是 |
| 已结束未参加 | `已结束` | 否 |

## 6. 前端实现建议

当前项目为 `RuoYi-Vue3`，技术栈包含：

- Vue 3
- Vite
- Element Plus
- ECharts
- Pinia
- Vue Router

建议新增文件：

```text
automation/RuoYi-Vue3/src/views/training/dashboard/index.vue
automation/RuoYi-Vue3/src/api/training/dashboard.js
automation/RuoYi-Vue3/src/views/training/dashboard/components/UserLearningSummaryCard.vue
automation/RuoYi-Vue3/src/views/training/dashboard/components/LearningTrendChart.vue
automation/RuoYi-Vue3/src/views/training/dashboard/components/QuickEntryPanel.vue
automation/RuoYi-Vue3/src/views/training/dashboard/components/DashboardListCard.vue
```

说明：

- `index.vue` 负责页面布局、接口聚合、loading/error 状态。
- `DashboardListCard.vue` 建议做成通用列表卡片，用 `type` 或插槽区分待办、通知、培训、考试。
- 图表独立封装，避免首页逻辑和 ECharts 初始化代码混在一起。
- API 使用若依现有 `request` 封装，接口统一放到 `src/api/training/dashboard.js`。

路由建议：

```js
{
  path: '/training/dashboard',
  component: Layout,
  hidden: false,
  children: [
    {
      path: 'index',
      component: () => import('@/views/training/dashboard/index'),
      name: 'TrainingDashboard',
      meta: { title: '学习工作台', icon: 'education', affix: true }
    }
  ]
}
```

若项目菜单由后端动态下发，则只需在后台菜单管理中配置对应路由与组件路径。

## 7. 接口设计建议

### 7.1 首页聚合接口

接口：

```http
GET /training/dashboard
```

返回示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "userSummary": {
      "userId": 1001,
      "name": "张华强",
      "avatar": "",
      "employeeNo": "ST20240982",
      "secretLevel": "二级空域",
      "statusText": "在职",
      "metrics": [
        { "key": "yearTrainingHours", "label": "本年度培训", "value": 128, "unit": "h" },
        { "key": "yearLearningHours", "label": "本年度学习", "value": 256, "unit": "h" },
        { "key": "totalLearningHours", "label": "总学习时", "value": 1240, "unit": "h" },
        { "key": "totalTrainingHours", "label": "总培训时", "value": 860, "unit": "h" }
      ]
    },
    "trend": [
      { "month": "1月", "learningHours": 12, "trainingHours": 8 },
      { "month": "2月", "learningHours": 18, "trainingHours": 12 },
      { "month": "3月", "learningHours": 20, "trainingHours": 18 }
    ],
    "quickEntries": [
      { "key": "courseLibrary", "title": "课程库", "icon": "Reading", "badge": 0, "path": "/training/course-library" },
      { "key": "myCourses", "title": "我的课程", "icon": "School", "badge": 12, "path": "/training/my-courses" },
      { "key": "myCertificates", "title": "我的证书", "icon": "Medal", "badge": 6, "path": "/training/my-certificates" },
      { "key": "examResults", "title": "考试成绩", "icon": "Document", "badge": 0, "path": "/training/exam-results" }
    ],
    "todoProcesses": [],
    "offlineNotices": [],
    "onlineTrainings": [],
    "onlineExams": []
  }
}
```

### 7.2 分模块接口

如果后端不适合一次性聚合，也可以拆分：

| 接口 | 用途 |
| --- | --- |
| `GET /training/dashboard/user-summary` | 用户学习概览 |
| `GET /training/dashboard/trend?year=2026` | 年度趋势 |
| `GET /training/dashboard/quick-entries` | 快捷入口与角标 |
| `GET /training/dashboard/todo-processes` | 我的待办流程 |
| `GET /training/dashboard/offline-notices` | 线下培训通知 |
| `GET /training/dashboard/online-trainings` | 线上培训通知 |
| `GET /training/dashboard/online-exams` | 在线考试通知 |

## 8. 数据模型建议

```ts
type DashboardListItem = {
  id: string | number
  title: string
  subtitle?: string
  source?: string
  ownerName?: string
  startTime?: string
  endTime?: string
  publishDate?: string
  publicFlag?: boolean
  status?: string
  statusText?: string
  actionText?: string
  actionPath?: string
}
```

字段说明：

- `title`：列表主标题，必填。
- `subtitle`：所属部门、课程简介、考试说明等副信息。
- `ownerName`：负责人、发布人或发起人。
- `startTime/endTime`：培训/考试起止时间。
- `publicFlag`：是否公开。
- `status/statusText`：学习状态、考试状态、流程状态。
- `actionText/actionPath`：首页操作按钮文案和跳转地址。

## 9. 交互与状态规则

加载状态：

- 页面首次进入时展示卡片骨架屏。
- 单个模块接口失败时，只在对应卡片展示错误状态，不阻塞其他模块。

空状态：

- 待办：`暂无待办流程`
- 线下通知：`暂无线下培训通知`
- 线上培训：`暂无线上培训`
- 在线考试：`暂无在线考试`

权限：

- 快捷入口和列表操作按钮需根据用户菜单权限过滤。
- 用户无权限的入口不展示，避免点击后 401。

跳转：

- `查看全部` 进入对应列表页。
- `办理` 进入流程详情。
- `继续学习/开始学习` 进入课程学习页。
- `开始考试` 进入考试作答页，进入前建议校验考试时间和用户资格。

## 10. 响应式要求

| 视口 | 布局 |
| --- | --- |
| `>= 1200px` | 第一行三列，下面两列 |
| `768px - 1199px` | 第一行两列，快捷入口可独占一行；下面两列或单列按空间调整 |
| `< 768px` | 全部单列，卡片内列表内容允许换行 |

移动端注意：

- 快捷入口改为四宫格。
- 列表行的时间、负责人可折行展示。
- 操作按钮保持在右侧或下一行右对齐，避免挤压标题。

## 11. 验收标准

- 页面整体视觉与效果图一致：浅灰背景、白色卡片、三行信息结构、红框标注模块均已实现。
- 用户概览展示头像、姓名、工号、密级和四个学习统计指标。
- 年度趋势图可正常渲染，包含学习时长和培训时长两条折线。
- 快捷入口四项完整展示，角标按接口数量显示或隐藏。
- 四类列表均支持标题、查看全部、列表项、负责人/时间、状态标签和操作按钮。
- 接口 loading、空数据、异常状态均有明确展示。
- 页面在 `1440px`、`1200px`、`768px`、`375px` 宽度下无文字重叠和横向溢出。
- 所有跳转使用 Vue Router，不直接刷新页面。

## 12. 待确认事项

- 首页最终路由是否替换当前 `/index`，还是新增为 `/training/dashboard`。
- 快捷入口的真实业务路由和权限标识。
- `线下培训通知` 中操作按钮文案是固定 `办理`，还是按通知类型显示 `查看/报名/办理`。
- 学习时长、培训时长的单位是否统一为小时，是否需要保留小数。
- 考试开始前是否需要弹窗确认、身份验证或防作弊环境检查。
