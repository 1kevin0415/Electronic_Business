package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.ApiResponse;
import com.dd.electronicbusiness.model.Order;
import com.dd.electronicbusiness.model.OrderDTO;
import com.dd.electronicbusiness.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ApiResponse<Order> createOrder(@RequestBody Order order) {
        return ApiResponse.success(orderService.createOrder(order));
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getAllOrders() {
        return ApiResponse.success(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ApiResponse<Order> getOrderById(@PathVariable Long id) {
        return ApiResponse.success(orderService.getOrderWithItemsById(id));
    }

    @GetMapping(params = "status")
    public ApiResponse<List<Order>> getOrdersByStatus(@RequestParam String status) {
        return ApiResponse.success(orderService.getOrdersByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String newStatus = payload.get("status");
        if (newStatus == null || newStatus.isEmpty()) {
            throw new IllegalArgumentException("新的状态值不能为空");
        }
        return ApiResponse.success(orderService.updateOrderStatus(id, newStatus));
    }
}