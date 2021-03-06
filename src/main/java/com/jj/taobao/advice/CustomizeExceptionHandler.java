package com.jj.taobao.advice;

import com.alibaba.fastjson.JSON;
import com.jj.taobao.dto.ResultDto;
import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
        @ExceptionHandler(Exception.class)
        ModelAndView handle(Throwable e,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
            String contentType = request.getContentType();
            if ("application/json".equals(contentType)){
                // 返回json
                ResultDto resultDto;
                if (e instanceof CustomizeException) {
                    resultDto =  ResultDto.errorOf((CustomizeException)e);
                } else {
                    resultDto =  ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
                }
                try {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.setStatus(200);
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(resultDto));
                    writer.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                return null;
            }else{
                // 页面跳转
                if (e instanceof CustomizeException) {
                    model.addAttribute("message",e.getMessage());
                } else {
                    model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
                }
                return new ModelAndView("error");
            }
        }
}