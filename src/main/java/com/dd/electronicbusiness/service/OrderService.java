package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.OrderMapper;
import com.dd.electronicbusiness.dao.ProductMapper;
import com.dd.electronicbusiness.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 创建新订单
     * @Transactional 注解确保这个方法中的所有数据库操作都在一个事务中执行。
     * 如果任何一步失败，所有已执行的操作都将回滚，保证了数据的一致性。
     */
    @Transactional
    public Order createOrder(Order order) {
        // 1. 设置订单创建时间
        order.setOrderDate(new Date());
        order.setStatus("待支付"); // 初始状态

        // 2. 将订单基本信息插入到 `orders` 表
        // 因为我们在OrderMapper.xml中配置了useGeneratedKeys,
        // 所以执行完这句后，order对象的ID会自动被赋值。
        orderMapper.saveOrder(order);

        // 3. 为每一个OrderItem设置其所属的OrderID
        for (OrderItemDTO item : order.getOrderItems()) {
            item.setOrderId(order.getId());

            Product product = productMapper.findById(item.getProductId());
            if (product != null) {
                int newStock = product.getStock() - item.getQuantity();
                if (newStock < 0) {
                    throw new RuntimeException("商品 " + product.getName() + " 库存不足！");
                }
                product.setStock(newStock);
                productMapper.update(product);
            }
        }

        // 5. 将所有订单项批量插入到 `order_items` 表
        orderMapper.saveOrderItems(order.getOrderItems());

        return order;
    }

    public List<OrderDTO> getAllOrders() {
        return orderMapper.findAllOrders();
    }

    public Order getOrderWithItemsById(Long id) {
        return orderMapper.findOrderWithItemsById(id);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderMapper.findOrdersByStatus(status);
    }
    // 在 OrderService.java 中添加这个新方法
    @Transactional
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderMapper.findOrderWithItemsById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在，ID: " + orderId);
        }
        order.setStatus(newStatus);
        orderMapper.update(order);
        return order;
    }
}