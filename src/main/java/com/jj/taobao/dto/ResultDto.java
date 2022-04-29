package com.jj.taobao.dto;

import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDto errorOf(Integer code,String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode noLogin) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(noLogin.getCode());
        resultDto.setMessage(noLogin.getMessage());
        return resultDto;
    }

    public static ResultDto okOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    public static <T> ResultDto okOf(T t) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
}
