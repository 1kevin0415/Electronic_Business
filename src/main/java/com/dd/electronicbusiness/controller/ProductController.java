package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.dao.ProductMapper;
import com.dd.electronicbusiness.model.Product;
import com.dd.electronicbusiness.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FileStorageService fileStorageService;

    // ... 省略其他 GET, PUT, DELETE 方法 ...
    @GetMapping
    public List<Product> getAllProducts() { return productMapper.findAll(); }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) { return productMapper.findById(id); }
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) { /*...*/ return product; }
    @PostMapping("/update/{id}")
    public Product updateProductWithFile(@PathVariable Long id,
                                         Product product, // Spring Boot 会自动绑定表单中的同名字段
                                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        System.out.println("\n\n--- [调试日志] 开始处理更新商品请求: ID = " + id + " ---");

        // 1. 从数据库中获取最新的商品数据
        Product existingProduct = productMapper.findById(id);
        if (existingProduct == null) {
            // 如果找不到商品，可以抛出异常或返回错误信息
            throw new RuntimeException("商品不存在，ID: " + id);
        }

        // 2. 更新商品信息
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        // 3. 如果上传了新图片，则更新图片URL
        if (imageFile != null && !imageFile.isEmpty()) {
            System.out.println("[调试日志] 接收到新的替换图片: " + imageFile.getOriginalFilename());
            // (可选) 在这里可以加上删除旧图片的逻辑
            String newFileName = fileStorageService.storeFile(imageFile);
            existingProduct.setImageUrl(newFileName);
            System.out.println("[调试日志] 图片已更新，新文件名: " + newFileName);
        } else {
            System.out.println("[调试日志] 本次更新未上传新图片。");
        }

        // 4. 将更新后的商品信息存入数据库
        System.out.println("[调试日志] 准备将更新后的 product 对象存入数据库: " + existingProduct.toString());
        productMapper.update(existingProduct);
        System.out.println("[调试日志] 数据库更新操作完成。");
        System.out.println("--- [调试日志] 更新商品请求处理完毕 ---\n\n");

        return existingProduct;
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) { productMapper.deleteById(id); }


    /**
     * 这是带有详细调试日志的 createProduct 方法
     */
    @PostMapping
    public Product createProduct(@RequestParam("name") String name,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam("price") BigDecimal price,
                                 @RequestParam("stock") Integer stock,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        System.out.println("\n\n--- [调试日志] 开始处理新增商品请求 ---");
        System.out.println("[调试日志] 接收到的商品名称: " + name);

        // 检查接收到的文件
        if (imageFile != null && !imageFile.isEmpty()) {
            System.out.println("[调试日志] 成功接收到图片文件!");
            System.out.println("[调试日志] 原始文件名: " + imageFile.getOriginalFilename());
            System.out.println("[调试日志] 文件大小: " + imageFile.getSize() + " bytes");
        } else {
            System.out.println("[调试日志] 警告：未接收到图片文件或文件为空!");
        }

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        // 再次检查文件，然后保存
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(imageFile);
            System.out.println("[调试日志] 文件已保存，生成的新文件名: " + fileName);
            product.setImageUrl(fileName);
        } else {
            product.setImageUrl(null);
        }

        System.out.println("[调试日志] 准备将 product 对象存入数据库: " + product.toString());
        productMapper.insert(product);
        System.out.println("[调试日志] 数据库插入操作完成。");
        System.out.println("--- [调试日志] 新增商品请求处理完毕 ---\n\n");

        return product;
    }
    @GetMapping("/images/show/{fileName:.+}")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> serveFile(@PathVariable String fileName) {
        org.springframework.core.io.Resource resource = fileStorageService.loadFileAsResource(fileName);
        return org.springframework.http.ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}