create table `comment `
(
    id BIGINT auto_increment,
    parent_id BIGINT null,
    type INT null,
    commentator BIGINT null,
    gmt_create BIGINT null,
    gmt_modified BIGINT null,
    like_count BIGINT null,
    constraint `comment _pk`
        primary key (id)
);
