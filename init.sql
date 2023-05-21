CREATE
    DATABASE sunmote;

CREATE TABLE User
(
    id         BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    customerId BIGINT UNSIGNED                       NULL COMMENT '所属客户',
    username   VARCHAR(64)                           NOT NULL COMMENT '用户名',
    password   VARCHAR(255)                          NOT NULL COMMENT '密码',
    role       VARCHAR(64) DEFAULT 'USER'            NOT NULL COMMENT '角色',
    status     TINYINT     DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete   TINYINT     DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    updatedAt  DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    createdAt  DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
)
    COMMENT '用户表';

INSERT INTO sunmote.User (username, password, status, isDelete, role)
    VALUE ('admin', '$2a$10$m4Zpfj/pWDfqV4sBqs6f0O9mTGwOnVhkIDk90u2vieHjxGzxXNZii', 0, 0, 'ADMIN');


CREATE TABLE Customer
(
    id              BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    corpName        VARCHAR(64)                           NULL,
    corpSubject     VARCHAR(64)                           NULL COMMENT '客户主体',
    cooperationMode VARCHAR(64)                           NULL COMMENT '合作模式：自投/代投',
    settlement      VARCHAR(64)                           NULL COMMENT '预付（按充值）、预付（按消耗）、后付周结、后付月结、后付账期',
    currency        VARCHAR(32)                           NULL COMMENT '美金/人民币/USDT/奈拉/比索',
    rebate          DOUBLE      DEFAULT 0.12              NOT NULL COMMENT '返点',
    serviceFee      DOUBLE                                NULL COMMENT '服务费政策',
    remarks         VARCHAR(128)                          NULL COMMENT '备注',
    source          VARCHAR(128)                          NULL COMMENT '客户来源',
    role            VARCHAR(64) DEFAULT 'USER'            NOT NULL COMMENT '角色',
    status          TINYINT     DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete        TINYINT     DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    updatedAt       DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    createdAt       DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
)
    COMMENT '客户表';


CREATE TABLE sunmote.CustomerAccount
(
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customerId      BIGINT UNSIGNED                    NULL COMMENT '客户ID',
    accountName     VARCHAR(255)                       NULL COMMENT '账号名',
    accountId       VARCHAR(255)                       NOT NULL COMMENT '账号id',

    budgetLimit     DOUBLE                             NULL COMMENT '预算总额',
    remainingAmount DOUBLE                             NULL COMMENT '剩余预算',
    costAmount      DOUBLE                             NULL COMMENT '花费总额',
    platform        VARCHAR(64)                        NOT NULL COMMENT '账号所属平台: Google、Facebook、Twitter...',
    currency        VARCHAR(32)                        NULL COMMENT '美金/人民币/USDT/奈拉/比索',

    status          TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete        TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户拥有的账号表';

CREATE TABLE sunmote.AccountBill
(
    id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    accountId VARCHAR(255)                       NOT NULL COMMENT '账号id',
    platform  VARCHAR(64)                        NOT NULL COMMENT '账号所属平台: Google、Facebook、Twitter...',
    amount    DOUBLE                             NOT NULL COMMENT '消费额',
    date      VARCHAR(64)                        NOT NULL COMMENT '日期 格式 YYYY-MM-DD',

    status    TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete  TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户账户账单表';

CREATE TABLE sunmote.CustomerPayment
(
    id         BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    amount     DOUBLE                             NULL COMMENT '付款金额',
    customerId BIGINT UNSIGNED                    NOT NULL COMMENT '客户ID',
    remark     VARCHAR(255)                       NULL COMMENT '邮箱',

    status     TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete   TINYINT  DEFAULT 0                 NOT NULL,
    createdAt  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '客户付款';


CREATE TABLE sunmote.AccountRecharge
(
    id             BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    accountId      VARCHAR(32)                        NOT NULL COMMENT '账户ID',
    rechargeAmount DOUBLE                             NOT NULL COMMENT '充值金额',

    status         TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete       TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '账户充值';

