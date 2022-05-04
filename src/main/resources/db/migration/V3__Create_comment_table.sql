CREATE TABLE `COMMENT`
(
    ID            BIGINT AUTO_INCREMENT,
    PARENT_ID     BIGINT        NULL,
    TYPE          INT           NULL,
    COMMENTATOR   BIGINT        NULL,
    GMT_CREATE    BIGINT        NULL,
    GMT_MODIFIED  BIGINT        NULL,
    LIKE_COUNT    BIGINT        NULL,
    COMMENT_COUNT INT DEFAULT 0 NULL,
    CONTENT       VARCHAR(1024),
    CONSTRAINT `COMMENT _PK`
        PRIMARY KEY (ID)
);
