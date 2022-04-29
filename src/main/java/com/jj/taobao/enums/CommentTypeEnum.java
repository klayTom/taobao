package com.jj.taobao.enums;

public enum CommentTypeEnum {
    PRODUCT(1),
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
