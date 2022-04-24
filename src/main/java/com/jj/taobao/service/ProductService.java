package com.jj.taobao.service;

import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;

    public List<ProductDto> list(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            User user = userMapper.selectByPrimaryKey(product.getCreator());
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product,productDto);
            productDto.setUser(user);
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
