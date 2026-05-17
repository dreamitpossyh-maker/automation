drop table if exists tr_learning_dashboard_summary;
create table tr_learning_dashboard_summary (
    summary_id bigint not null auto_increment comment '主键ID',
    user_id bigint not null default 0 comment '用户ID',
    summary_year int not null comment '统计年份',
    display_name varchar(64) not null comment '展示姓名',
    avatar varchar(255) default '' comment '头像地址',
    employee_no varchar(32) default '' comment '工号',
    secret_level varchar(32) default '' comment '密级',
    status_text varchar(32) default '' comment '身份状态',
    year_training_hours decimal(10,1) not null default 0.0 comment '本年度培训时长',
    year_learning_hours decimal(10,1) not null default 0.0 comment '本年度学习时长',
    total_learning_hours decimal(10,1) not null default 0.0 comment '总学习时长',
    total_training_hours decimal(10,1) not null default 0.0 comment '总培训时长',
    sort_num int not null default 0 comment '排序',
    status char(1) not null default '0' comment '状态（0正常 1停用）',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime default null comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime default null comment '更新时间',
    remark varchar(500) default null comment '备注',
    primary key (summary_id)
) engine=InnoDB auto_increment=1 default charset=utf8mb4 comment='学习工作台概览表';

drop table if exists tr_learning_dashboard_trend;
create table tr_learning_dashboard_trend (
    trend_id bigint not null auto_increment comment '主键ID',
    user_id bigint not null default 0 comment '用户ID',
    stat_year int not null comment '统计年份',
    month_no int not null comment '月份序号',
    month_label varchar(16) not null comment '月份显示',
    learning_hours decimal(10,1) not null default 0.0 comment '学习时长',
    training_hours decimal(10,1) not null default 0.0 comment '培训时长',
    sort_num int not null default 0 comment '排序',
    status char(1) not null default '0' comment '状态（0正常 1停用）',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime default null comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime default null comment '更新时间',
    remark varchar(500) default null comment '备注',
    primary key (trend_id)
) engine=InnoDB auto_increment=1 default charset=utf8mb4 comment='学习工作台趋势表';

drop table if exists tr_learning_dashboard_item;
create table tr_learning_dashboard_item (
    item_id bigint not null auto_increment comment '主键ID',
    user_id bigint not null default 0 comment '用户ID',
    item_type varchar(32) not null comment '事项类型',
    title varchar(200) not null comment '标题',
    subtitle varchar(200) default '' comment '副标题',
    source_label varchar(64) default '' comment '来源标签',
    owner_name varchar(64) default '' comment '负责人',
    start_time datetime default null comment '开始时间',
    end_time datetime default null comment '结束时间',
    publish_date date default null comment '发布日期',
    public_flag char(1) not null default '1' comment '是否公开（1是 0否）',
    business_status varchar(32) default '' comment '业务状态',
    status_text varchar(32) default '' comment '状态文案',
    action_text varchar(32) default '' comment '按钮文案',
    action_type varchar(32) default '' comment '按钮类型',
    action_path varchar(255) default '' comment '跳转路径',
    badge_count int not null default 0 comment '角标数量',
    sort_num int not null default 0 comment '排序',
    status char(1) not null default '0' comment '状态（0正常 1停用）',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime default null comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime default null comment '更新时间',
    remark varchar(500) default null comment '备注',
    primary key (item_id)
) engine=InnoDB auto_increment=1 default charset=utf8mb4 comment='学习工作台事项表';

insert into tr_learning_dashboard_summary
(user_id, summary_year, display_name, avatar, employee_no, secret_level, status_text, year_training_hours, year_learning_hours, total_learning_hours, total_training_hours, sort_num, status, create_by, create_time, remark)
values
(0, 2026, '张华强', '', 'ST20240982', '二级空域', '在职', 128.0, 256.0, 1240.0, 860.0, 1, '0', 'admin', sysdate(), '首页默认概览数据');

insert into tr_learning_dashboard_trend
(user_id, stat_year, month_no, month_label, learning_hours, training_hours, sort_num, status, create_by, create_time, remark)
values
(0, 2026, 1, '1月', 12.0, 8.0, 1, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 2, '2月', 18.0, 12.0, 2, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 3, '3月', 20.0, 15.0, 3, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 4, '4月', 25.0, 18.0, 4, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 5, '5月', 30.0, 22.0, 5, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 6, '6月', 22.0, 25.0, 6, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 7, '7月', 28.0, 20.0, 7, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 8, '8月', 35.0, 28.0, 8, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 9, '9月', 40.0, 22.0, 9, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 10, '10月', 32.0, 30.0, 10, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 11, '11月', 25.0, 38.0, 11, '0', 'admin', sysdate(), '首页默认趋势数据'),
(0, 2026, 12, '12月', 38.0, 28.0, 12, '0', 'admin', sysdate(), '首页默认趋势数据');

insert into tr_learning_dashboard_item
(user_id, item_type, title, subtitle, source_label, owner_name, start_time, end_time, publish_date, public_flag, business_status, status_text, action_text, action_type, action_path, badge_count, sort_num, status, create_by, create_time, remark)
values
(0, 'TODO_PROCESS', '课程审批：Python数据分析进阶', '所属部门：培训中心', '学习流程', '王明亮', null, null, null, '1', 'PENDING', '待办理', '办理', 'NONE', '', 0, 1, '0', 'admin', sysdate(), '首页默认待办'),
(0, 'TODO_PROCESS', '考试补考申请：安全生产规范', '所属部门：安全管理部', '考试流程', '李璇', null, null, null, '1', 'PENDING', '待办理', '办理', 'NONE', '', 0, 2, '0', 'admin', sysdate(), '首页默认待办'),
(0, 'TODO_PROCESS', '培训学时认定：人工智能研讨会', '所属部门：人力资源部', '认定流程', '陈建国', null, null, null, '1', 'PENDING', '待办理', '办理', 'NONE', '', 0, 3, '0', 'admin', sysdate(), '首页默认待办'),
(0, 'TODO_PROCESS', '讲师入库资格初审', '所属部门：教研中心', '资格初审', '周明', null, null, null, '1', 'PENDING', '待办理', '办理', 'NONE', '', 0, 4, '0', 'admin', sysdate(), '首页默认待办'),
(0, 'OFFLINE_NOTICE', '关于2026年第二季度线下课程更新的通知', '所属部门：培训部', '线下培训', '张系统', null, null, '2026-05-10', '1', 'OPEN', '已发布', '办理', 'NONE', '', 0, 1, '0', 'admin', sysdate(), '首页默认线下通知'),
(0, 'OFFLINE_NOTICE', '关于举行《数字化转型白皮书》解读会的通知', '所属部门：培训部', '线下培训', '李规划', null, null, '2026-05-15', '1', 'OPEN', '已发布', '办理', 'NONE', '', 0, 2, '0', 'admin', sysdate(), '首页默认线下通知'),
(0, 'OFFLINE_NOTICE', '您有一张证书即将过期，请及时参加换证培训', '所属部门：认证中心', '线下培训', '王质量', null, null, '2026-05-20', '1', 'OPEN', '已发布', '办理', 'NONE', '', 0, 3, '0', 'admin', sysdate(), '首页默认线下通知'),
(0, 'OFFLINE_NOTICE', '5月份“名师讲堂”排课计划公布', '所属部门：教务部', '线下培训', '赵教学', null, null, '2026-05-08', '1', 'OPEN', '已发布', '办理', 'NONE', '', 0, 4, '0', 'admin', sysdate(), '首页默认线下通知'),
(0, 'ONLINE_TRAINING', '高效沟通技巧（必修）', '适用于管理岗和业务骨干', '线上培训', '张晓东', '2026-05-01 09:00:00', '2026-06-01 18:00:00', null, '1', 'LEARNING', '学习中', '继续学习', 'ROUTE', '', 0, 1, '0', 'admin', sysdate(), '首页默认线上培训'),
(0, 'ONLINE_TRAINING', '云原生架构设计实践', '深入理解K8s、微服务和服务治理', '线上培训', '李工', '2026-04-15 00:00:00', '2026-05-30 23:59:00', null, '1', 'NOT_STARTED', '未学习', '开始学习', 'ROUTE', '', 0, 2, '0', 'admin', sysdate(), '首页默认线上培训'),
(0, 'ONLINE_TRAINING', 'React 19 新特性深入剖析', '全面了解 React 19 的核心变化', '线上培训', '王小明', '2026-05-05 10:00:00', '2026-06-15 18:00:00', null, '1', 'LEARNING', '学习中', '继续学习', 'ROUTE', '', 0, 3, '0', 'admin', sysdate(), '首页默认线上培训'),
(0, 'ONLINE_EXAM', '2026年度上半年消防安全知识考试', '全员必考，合格分数90分', '在线考试', '刘卫卫', '2026-05-01 08:00:00', '2026-05-15 18:00:00', null, '1', 'IN_PROGRESS', '进行中', '开始考试', 'EXAM_CONFIRM', '', 0, 1, '0', 'admin', sysdate(), '首页默认考试'),
(0, 'ONLINE_EXAM', '技术岗位资格认定考试', '岗位资格认定与基础能力测评', '在线考试', '陈处长', '2026-05-07 14:00:00', '2026-05-07 16:00:00', null, '1', 'IN_PROGRESS', '进行中', '开始考试', 'EXAM_CONFIRM', '', 0, 2, '0', 'admin', sysdate(), '首页默认考试'),
(0, 'ONLINE_EXAM', '《员工手册》2026版合规测试', '了解企业文化与制度规范', '在线考试', '孙主任', '2026-05-01 00:00:00', '2026-05-31 23:59:00', null, '1', 'IN_PROGRESS', '进行中', '开始考试', 'EXAM_CONFIRM', '', 0, 3, '0', 'admin', sysdate(), '首页默认考试');
