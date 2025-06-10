package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.Order;
import com.dd.electronicbusiness.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // 当前端发送POST请求到 /api/orders 时，
        // 请求体中的JSON数据会被自动转换为一个Order对象。
        // 然后我们调用OrderService来处理创建订单的业务逻辑。
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderWithItemsById(id);
    }
}