package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Order;
import com.dd.electronicbusiness.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入一条新的订单记录
     * @param order 订单对象
     */
    void saveOrder(Order order);

    /**
     * 批量插入订单中的所有商品项
     * @param orderItems 订单商品项列表
     */
    void saveOrderItems(List<OrderItem> orderItems);
    List<Order> findAllOrders();

    Order findOrderWithItemsById(Long id);

    void update(Order order);
}