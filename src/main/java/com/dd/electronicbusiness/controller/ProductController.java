package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.ApiResponse;
import com.dd.electronicbusiness.model.Product;
import com.dd.electronicbusiness.service.FileStorageService;
import com.dd.electronicbusiness.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {
        return ApiResponse.success(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        return ApiResponse.success(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ApiResponse.success(null);
    }

    @PostMapping
    public ApiResponse<Product> createProduct(@RequestParam("name") String name,
                                              @RequestParam(value = "description", required = false) String description,
                                              @RequestParam("price") BigDecimal price,
                                              @RequestParam("stock") Integer stock,
                                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(imageFile);
            product.setImageUrl(fileName);
        } else {
            product.setImageUrl(null);
        }

        productService.createProduct(product);
        return ApiResponse.success(product);
    }

    @PostMapping("/update/{id}")
    public ApiResponse<Product> updateProductWithFile(@PathVariable Long id, Product product, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            throw new RuntimeException("商品不存在，ID: " + id);
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        if (imageFile != null && !imageFile.isEmpty()) {
            String newFileName = fileStorageService.storeFile(imageFile);
            existingProduct.setImageUrl(newFileName);
        }

        productService.updateProduct(existingProduct);
        return ApiResponse.success(existingProduct);
    }

    @GetMapping("/images/show/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
