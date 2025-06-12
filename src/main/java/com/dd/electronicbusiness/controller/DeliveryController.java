package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.Delivery;
import com.dd.electronicbusiness.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // API: 获取所有配送列表
    // 访问方式: GET http://localhost:8080/api/deliveries
    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    // API: 创建新配送（为订单发货）
    // 访问方式: POST http://localhost:8080/api/deliveries
    @PostMapping
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        return deliveryService.createDelivery(delivery);
    }
}