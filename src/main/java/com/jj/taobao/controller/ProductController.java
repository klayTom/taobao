package com.jj.taobao.controller;

import com.jj.taobao.dto.CommentDto;
import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.enums.CommentTypeEnum;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.service.CommentService;
import com.jj.taobao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductService productService;
    @Autowired
    CommentService commentService;

    @GetMapping("/product/{id}")
    public String product(@PathVariable("id")Long id, Model model) {
        ProductDto productDto = productService.productDtoById(id);
        List<CommentDto> comments = commentService.listByTargetId(id, CommentTypeEnum.PRODUCT);
        List<Product> products = productService.selectRelated(productDto);
        productService.inViewCount(id);
        model.addAttribute("product",productDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedProducts",products);
        return "product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id) {
        productService.deleteById(id);
        return "redirect:/";
    }
}
