CREATE TABLE NOTIFICATION
(
    ID            BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    NOTIFIER      BIGINT        NOT NULL,
    RECEIVER      BIGINT        NOT NULL,
    OUTER_ID      BIGINT        NOT NULL,
    TYPE          INT           NOT NULL,
    GMT_CREATE    BIGINT        NOT NULL,
    STATUS        INT DEFAULT 0 NOT NULL,
    NOTIFIER_NAME VARCHAR(100)  NULL,
    OUTER_TITLE   VARCHAR(256)  NULL
);