package com.jj.taobao.controller;

import com.jj.taobao.cache.TagCache;
import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.User;
import com.jj.taobao.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    ProductService productService;


    @GetMapping("publish")
    public String publish(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(HttpServletRequest httpServletRequest,
                       Model model,
                       @PathVariable("id")Long id) {
        Product product = productService.productById(id);
        model.addAttribute("title",product.getTitle());
        model.addAttribute("description",product.getDescription());
        model.addAttribute("tag",product.getTag());
        model.addAttribute("id",id);
        model.addAttribute("tags", TagCache.get());
        return "publish";

    }

    @PostMapping("publish")
    public String doPublish(HttpServletRequest httpServletRequest,
                            Model model,
                            @RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id", required = false)Long id,
                            @RequestParam(value = "filePath", required = false)String filePath) {
        User user =(User)httpServletRequest.getSession().getAttribute("user");
        if (user==null) {
            model.addAttribute("error","用户未登录");
            return "login";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
        if ("".equals(title)) {
            model.addAttribute("error","商品名称不能为空");
            return "publish";
        }
        if ("".equals(description)) {
            model.addAttribute("error","商品描述不能为空");
            return "publish";
        }
        if ("".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error","输入非法标签:" + invalid);
            return "publish";
        }
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setTag(tag);
        product.setGmtCreate(System.currentTimeMillis());
        product.setCreator(user.getId());
        product.setId(id);
        productService.createOrUpdate(product);
        return "redirect:/";

    }
}
