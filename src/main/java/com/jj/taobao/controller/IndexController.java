package com.jj.taobao.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.User;
import com.jj.taobao.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    UserMapper userMapper;

    @GetMapping({"/","index"})
    public String toIndex(Model model,
                          @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectByExample(null);
        List<ProductDto> productDtos = productService.list(products);

        //取消PageInfo的类型定义，因为PageInfo 需要兼容products和productDtos两种类型
        PageInfo pageInfo = new PageInfo(products, 5);
        //替换PageInfo实际结果集为处理后的数据productDtos
        pageInfo.setList(productDtos);

        model.addAttribute("products",productDtos);
        model.addAttribute("pageInfo",pageInfo);

        return "index";
    }
}
