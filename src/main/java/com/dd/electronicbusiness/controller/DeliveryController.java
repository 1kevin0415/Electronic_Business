package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.ApiResponse;
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

    @GetMapping
    public ApiResponse<List<Delivery>> getAllDeliveries() {
        return ApiResponse.success(deliveryService.getAllDeliveries());
    }

    @PostMapping
    public ApiResponse<Delivery> createDelivery(@RequestBody Delivery delivery) {
        return ApiResponse.success(deliveryService.createDelivery(delivery));
    }
}
