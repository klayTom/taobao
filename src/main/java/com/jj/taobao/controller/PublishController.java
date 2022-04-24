package com.jj.taobao.controller;

import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    ProductMapper productMapper;


    @GetMapping("publish")
    public String publish(HttpServletRequest httpServletRequest, Model model) {

        return "publish";
    }
    @PostMapping("publish")
    public String doPublish(HttpServletRequest httpServletRequest,
                            Model model,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag) {
        User user =(User)httpServletRequest.getSession().getAttribute("user");
        if (user==null) {
            model.addAttribute("error","用户未登录");
            return "login";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if ("".equals(title)) {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if ("".equals(description)) {
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if ("".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setTag(tag);
        product.setGmtCreate(System.currentTimeMillis());
        product.setGmtModified(System.currentTimeMillis());
        product.setCreator(user.getId());
        productMapper.insert(product);
        return "redirect:/";

    }
}
