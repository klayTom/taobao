package com.jj.taobao.interceptor;

import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.User;
import com.jj.taobao.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user =(User) request.getSession().getAttribute("user");
        if ( user != null) {
            Long unreadCount = notificationService.unreadCount(user.getId());
            request.getSession().setAttribute("unreadCount",unreadCount);
        }
        return true;
    }
}
