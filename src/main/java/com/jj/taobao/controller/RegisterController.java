package com.jj.taobao.controller;

import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.User;
import com.jj.taobao.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        if (users.size()>0) {
            model.addAttribute("msg","用户名已存在");
            return "register";
        }
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl("/images/user-36.png");
        userMapper.insert(user);
        return "redirect:/";
    }
}
