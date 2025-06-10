package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // 1. 导入 @Param 注解
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    // 2. 为 insert 方法的参数添加 @Param("p")
    int insert(@Param("p") Product product);

    int deleteById(Long id);

    // 3. 为 update 方法的参数添加 @Param("p")
    int update(@Param("p") Product product);

    Product findById(Long id);
}