package com.jj.taobao.controller;

import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.User;
import com.jj.taobao.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest httpServletRequest, Model model, User user) {
        User currentUser = (User)httpServletRequest.getSession().getAttribute("user");
        if (currentUser != null) {
            return "index";
        }

        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        User dbUser = users.get(0);
        if (dbUser.getPassword().equals(user.getPassword())) {
            user.setId(dbUser.getId());
            user.setAvatarUrl(dbUser.getAvatarUrl());
            httpServletRequest.getSession().setAttribute("user",user);
            return "redirect:/";
        }
        model.addAttribute("msg","密码错误");
        return "login";

    }
}
