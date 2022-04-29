package com.jj.taobao.service;

import com.jj.taobao.dto.ProductDto;
import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.exception.CustomizeException;
import com.jj.taobao.mapper.ProductExtMapper;
import com.jj.taobao.mapper.ProductMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Product;
import com.jj.taobao.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductExtMapper productExtMapper;

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

    public ProductDto productDtoById(Long id) {
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            throw new CustomizeException(CustomizeErrorCode.Product_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(product.getCreator());
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        productDto.setUser(user);
        return productDto;
    }

    public void createOrUpdate(Product product) {
        if (product.getId() == null) {
            product.setGmtCreate(System.currentTimeMillis());
            product.setGmtModified(System.currentTimeMillis());
            product.setLikeCount(0);
            product.setViewCount(0);
            product.setCommentCount(0);
            productMapper.insert(product);
        } else {
            product.setGmtModified(System.currentTimeMillis());
            int result = productMapper.updateByPrimaryKey(product);
            if (result!=1) {
                throw new CustomizeException(CustomizeErrorCode.Product_NOT_FOUND);
            }
        }

    }

    public Product productById(Long id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    public void deleteById(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public void inViewCount(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setViewCount(1);
        productExtMapper.inViewCount(product);
    }

    public List<Product> selectRelated(ProductDto product) {
        if (StringUtils.isBlank(product.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(product.getTag(), ",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setTag(regexTag);
        List<Product> products = productExtMapper.selectRelated(newProduct);
        return products;
    }

    public List<Product> selectSearch(String search) {
        if (StringUtils.isNotBlank(search)) {
            String[] searches = StringUtils.split(search, " ");
            String regexSearch = Arrays.stream(searches).collect(Collectors.joining("|"));
            List<Product> products = productExtMapper.selectSearch(regexSearch);
            return products;
        }

        return new ArrayList<>();
    }


}
