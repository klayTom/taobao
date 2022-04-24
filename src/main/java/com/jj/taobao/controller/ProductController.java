package com.jj.taobao.controller;

import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;

    @GetMapping("product/{id}")
    public String product(@PathVariable("id")Long id, Model model) {
        Product product = productMapper.selectByPrimaryKey(id);
        model.addAttribute("product",product);
        return "product";
    }
}
