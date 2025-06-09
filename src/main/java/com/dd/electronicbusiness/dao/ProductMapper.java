package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Product;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();
    int insert(Product product);
    int deleteById(Long id);
    int update(Product product);
    Product findById(Long id);
}