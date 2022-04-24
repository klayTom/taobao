package com.jj.taobao.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.ProductExample;
import com.jj.taobao.model.User;
import com.jj.taobao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductService productService;
    @Autowired
    ProductMapper productMapper;

    @GetMapping("/profile/{action}")
    public String profile (HttpServletRequest request,
                           @PathVariable("action") String action,
                           Model model,
                           @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {
        User currentUser = (User)request.getSession().getAttribute("user");
        if (currentUser == null) {
            return "redirect:/";
        }

        if ("products".equals(action)) {
            model.addAttribute("section",action);
            model.addAttribute("sectionName","我的商品");
        } else if ("replies".equals(action)) {
            model.addAttribute("section",action);
            model.addAttribute("sectionName","最新回复");
        }

        ProductExample example = new ProductExample();
        example.createCriteria()
                        .andCreatorEqualTo(currentUser.getId());
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByExample(example);
        List<ProductDto> productDtos = productService.list(products);
        PageInfo pageInfo = new PageInfo(products,5);
        pageInfo.setList(productDtos);
        model.addAttribute("products",productDtos);
        model.addAttribute("pageInfo",pageInfo);

        return "profile";
    }
}
