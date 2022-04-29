package com.jj.taobao.controller;

import com.jj.taobao.dto.NotificationDto;
import com.jj.taobao.enums.NotificationTypeEnum;
import com.jj.taobao.model.User;
import com.jj.taobao.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile (HttpServletRequest request,
                           @PathVariable("id") Long id) {
        User user =(User) request.getSession().getAttribute("user");
        if (user==null) {
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                ||NotificationTypeEnum.REPLY_PRODUCT.getType() == notificationDto.getType()) {
            return "redirect:/product/" + notificationDto.getOuterId();
        }else {
            return "redirect:/";
        }

    }
}
