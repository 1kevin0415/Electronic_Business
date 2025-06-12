package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.ProductMapper;
import com.dd.electronicbusiness.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    // @Cacheable("products")
    // 当这个方法被调用时，Spring会先检查名为 "products" 的缓存中是否有数据。
    // 如果有，就直接返回缓存的数据，不再执行方法体。
    // 如果没有，就执行方法体（查询数据库），并将返回结果存入 "products" 缓存中。
    @Cacheable("products")
    public List<Product> getAllProducts() {
        // 为了让缓存效果更明显，我们可以打印一条日志来观察
        System.out.println("--- 正在从数据库查询商品列表... ---");
        return productMapper.findAll();
    }

    public Product getProductById(Long id) {
        return productMapper.findById(id);
    }

    // @CacheEvict(...)
    // 当我们新增、更新或删除商品时，缓存中的旧数据就过时了。
    // 这个注解的作用是：在方法成功执行后，清空名为 "products" 的缓存。
    // allEntries = true 表示清空该缓存下的所有条目。
    @CacheEvict(value = "products", allEntries = true)
    public void createProduct(Product product) {
        productMapper.insert(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(Product product) {
        productMapper.update(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProductById(Long id) {
        productMapper.deleteById(id);
    }
}