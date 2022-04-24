create table product
(
    id bigint auto_increment,
    title VARCHAR(50) null,
    description text null,
    gmt_create bigint null,
    gmt_modified bigint null,
    creator bigint null,
    comment_count int default 0 null,
    view_count int default 0 null,
    like_count int default 0 null,
    tag varchar(256) null,
    constraint product_pk
        primary key (id)
);
