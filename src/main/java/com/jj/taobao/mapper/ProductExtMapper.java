package com.jj.taobao.mapper;

import com.jj.taobao.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductExtMapper {
    int inViewCount(Product product);
    int inCommentCount(Product product);
    List<Product> selectRelated(Product product);
    List<Product> selectSearch(String search);
}