package com.jj.taobao.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    Product_NOT_FOUND(2001,"你找的商品不在了,换一个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登陆还不能评论,请先登录"),
    SYS_ERROR(2004,"服务器跑丢了,稍后再试吧"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了,换一个试试?"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"你这是读别人的信息?"),
    NOTIFICATION_NOT_FOUND(2009,"消息不存在了!!!"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
