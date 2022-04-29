package com.jj.taobao.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private int type;
}
