CREATE
DATABASE sunmote;

create table User
(
    id              bigint unsigned auto_increment
        primary key,
    username        varchar(64)                           not null comment '用户名',
    password        varchar(255)                          not null comment '密码',
    corpName        varchar(64)                           null,
    corpSubject     varchar(64)                           null comment '客户主体',
    cooperationMode varchar(64)                           null comment '合作模式：自投/代投',
    settlement      varchar(64)                           null comment '预付（按充值）、预付（按消耗）、后付周结、后付月结、后付账期',
    currency        varchar(32)                           null comment '美金/人民币/USDT/奈拉/比索',
    rebate          double      default 0.12              not null comment '返点',
    serviceFee      double                                null comment '服务费政策',
    remarks         varchar(128)                          null comment '备注',
    source          varchar(128)                          null comment '客户来源',
    role            varchar(64) default 'USER'            not null comment '角色',
    status          tinyint     default 0                 not null comment '状态',
    isDelete        tinyint     default 0                 not null comment '是否已删除',
    updatedAt       datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    createdAt       datetime    default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户表';


insert into sunmote.User (username, password, corpName, status, rebate, isDelete, role)
    value ('admin','$2a$10$m4Zpfj/pWDfqV4sBqs6f0O9mTGwOnVhkIDk90u2vieHjxGzxXNZii','光晨科技',0,0.12,0,'ADMIN');

create table sunmote.UserAccount
(
    id        bigint unsigned auto_increment primary key,
    userId    bigint unsigned not null comment '用户ID',
    accountId varchar(255)                       not null comment '账号id',
    platform  varchar(64)                        not null comment '账号所属平台: Google、Facebook、Twitter...',

    status    tinyint  default 0                 not null comment '状态',
    isDelete  tinyint  default 0                 not null comment '是否已删除',
    createdAt datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户拥有的账号表';

create table sunmote.AccountBill
(
    id        bigint unsigned auto_increment primary key,
    accountId varchar(255)                       not null comment '账号id',
    platform  varchar(64)                        not null comment '账号所属平台: Google、Facebook、Twitter...',
    amount    varchar(64)                        not null comment '消费额',

    status    tinyint  default 0                 not null comment '状态',
    isDelete  tinyint  default 0                 not null comment '是否已删除',
    createdAt datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户账户账单表';

create table sunmote.DepositApproval
(
    id        bigint unsigned auto_increment
        primary key,
    deposit   varchar(64) null comment '付款金额',
    userId    bigint unsigned not null comment '用户ID',
    remark    varchar(255) null comment '邮箱',
    currency  varchar(16) default 'USD'             not null,
    status    tinyint     default 0                 not null comment '状态',
    isDelete  tinyint     default 0                 not null,
    createdAt datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '付款申请';



create table sunmote.CreateAccountApproval
(
    id             bigint unsigned auto_increment
        primary key,
    timeZone       varchar(64)                           not null comment '时区',
    accountId      varchar(32) null comment '账户ID',
    currency       varchar(16) default 'USD'             not null comment '币种',
    rechargeAmount double      default 0 null comment '首次充值金额',
    prodLink       varchar(128) null comment '产品链接',
    userId         bigint unsigned not null comment '用户ID',
    status         tinyint     default 0                 not null comment '状态',
    isDelete       tinyint     default 0                 not null comment '是否已删除',
    createdAt      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt      datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '开户申请';



create table sunmote.AccountRechargeApproval
(
    id                   bigint unsigned auto_increment
        primary key,
    accountId            varchar(32)                        not null comment '账户ID',
    rechargeAmount       double                             not null comment '充值金额',
    payAmount            double                             not null comment '实际支付金额',
    targetCurrency       varchar(16)                        not null comment '目标币种',
    exchangeRate         double null comment '汇率',
    targetCurrencyAmount double null comment '汇率结算金额',
    userId               bigint unsigned not null comment '用户ID',
    status               tinyint  default 0                 not null comment '状态',
    isDelete             tinyint  default 0                 not null comment '是否已删除',
    createdAt            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt            datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '账户充值申请';

