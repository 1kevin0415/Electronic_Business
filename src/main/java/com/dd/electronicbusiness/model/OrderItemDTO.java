package com.dd.electronicbusiness.model;

// 这个类继承自OrderItem，拥有其所有属性
public class OrderItemDTO extends OrderItem {
    // 我们额外增加一个productName字段
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}