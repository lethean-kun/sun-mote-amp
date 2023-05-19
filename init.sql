CREATE
    DATABASE sunmote;

CREATE TABLE User (
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


CREATE TABLE Customer (
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


CREATE TABLE sunmote.CustomerAccount (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customerId BIGINT UNSIGNED                    NOT NULL COMMENT '客户ID',
    accountId  VARCHAR(255)                       NOT NULL COMMENT '账号id',
    platform   VARCHAR(64)                        NOT NULL COMMENT '账号所属平台: Google、Facebook、Twitter...',

    status     TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete   TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户拥有的账号表';

CREATE TABLE sunmote.AccountBill (
    id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    accountId VARCHAR(255)                       NOT NULL COMMENT '账号id',
    platform  VARCHAR(64)                        NOT NULL COMMENT '账号所属平台: Google、Facebook、Twitter...',
    amount    VARCHAR(64)                        NOT NULL COMMENT '消费额',

    status    TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete  TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户账户账单表';

CREATE TABLE sunmote.DepositApproval (
    id        BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    deposit   VARCHAR(64)                           NULL COMMENT '付款金额',
    userId    BIGINT UNSIGNED                       NOT NULL COMMENT '用户ID',
    remark    VARCHAR(255)                          NULL COMMENT '邮箱',
    currency  VARCHAR(16) DEFAULT 'USD'             NOT NULL,
    status    TINYINT     DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete  TINYINT     DEFAULT 0                 NOT NULL,
    createdAt DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '付款申请';



CREATE TABLE sunmote.CreateAccountApproval (
    id             BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    timeZone       VARCHAR(64)                           NOT NULL COMMENT '时区',
    accountId      VARCHAR(32)                           NULL COMMENT '账户ID',
    currency       VARCHAR(16) DEFAULT 'USD'             NOT NULL COMMENT '币种',
    rechargeAmount DOUBLE      DEFAULT 0                 NULL COMMENT '首次充值金额',
    prodLink       VARCHAR(128)                          NULL COMMENT '产品链接',
    userId         BIGINT UNSIGNED                       NOT NULL COMMENT '用户ID',
    status         TINYINT     DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete       TINYINT     DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt      DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt      DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '开户申请';



CREATE TABLE sunmote.AccountRechargeApproval (
    id                   BIGINT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    accountId            VARCHAR(32)                        NOT NULL COMMENT '账户ID',
    rechargeAmount       DOUBLE                             NOT NULL COMMENT '充值金额',
    payAmount            DOUBLE                             NOT NULL COMMENT '实际支付金额',
    targetCurrency       VARCHAR(16)                        NOT NULL COMMENT '目标币种',
    exchangeRate         DOUBLE                             NULL COMMENT '汇率',
    targetCurrencyAmount DOUBLE                             NULL COMMENT '汇率结算金额',
    userId               BIGINT UNSIGNED                    NOT NULL COMMENT '用户ID',
    status               TINYINT  DEFAULT 0                 NOT NULL COMMENT '状态',
    isDelete             TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否已删除',
    createdAt            DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updatedAt            DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '账户充值申请';

