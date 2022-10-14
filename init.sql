CREATE DATABASE sunmote;

create table sunmote.User
(
    id        bigint unsigned auto_increment primary key,
    username  varchar(64)                           not null comment '用户名',
    password  varchar(255)                          not null comment '密码',
    corpName  varchar(64)                           null,
    status    tinyint     default 0                 not null comment '状态',
    rebate    double      default 0.12              not null comment '返点',
    isDelete  tinyint     default 0                 not null comment '是否已删除',
    createdAt datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    role      varchar(64) default 'USER'            not null comment '角色'
) comment '用户表';

create table sunmote.DepositApproval
(
    id        bigint unsigned auto_increment
        primary key,
    deposit   varchar(64)                           null comment '付款金额',
    userId    bigint unsigned                       not null comment '用户ID',
    remark    varchar(255)                          null comment '邮箱',
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
    accountId      varchar(32)                           null comment '账户ID',
    currency       varchar(16) default 'USD'             not null comment '币种',
    rechargeAmount double      default 0                 null comment '首次充值金额',
    prodLink       varchar(128)                          null comment '产品链接',
    userId         bigint unsigned                       not null comment '用户ID',
    status         tinyint     default 0                 not null comment '状态',
    isDelete       tinyint     default 0                 not null comment '是否已删除',
    createdAt      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt      datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '开户申请';



create table sunmote.AccountRechargeApproval
(
    id                   bigint unsigned auto_increment
        primary key,
    accountId            varchar(32)                        not null comment '账户ID',
    rechargeAmount       double                             not null comment '充值金额',
    payAmount            double                             not null comment '实际支付金额',
    targetCurrency       varchar(16)                        not null comment '目标币种',
    exchangeRate         double                             null comment '汇率',
    targetCurrencyAmount double                             null comment '汇率结算金额',
    userId               bigint unsigned                    not null comment '用户ID',
    status               tinyint  default 0                 not null comment '状态',
    isDelete             tinyint  default 0                 not null comment '是否已删除',
    createdAt            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updatedAt            datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '账户充值申请';

