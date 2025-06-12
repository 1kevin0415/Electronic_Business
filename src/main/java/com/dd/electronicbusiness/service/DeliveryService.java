package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.DeliveryMapper;
import com.dd.electronicbusiness.dao.OrderMapper;
import com.dd.electronicbusiness.model.Delivery;
import com.dd.electronicbusiness.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private OrderMapper orderMapper; // 注入OrderMapper以更新订单状态

    /**
     * 获取所有配送信息
     */
    public List<Delivery> getAllDeliveries() {
        return deliveryMapper.findAllDeliveries();
    }

    /**
     * 创建一个新的配送（发货）
     * @Transactional 注解保证了“创建配送记录”和“更新订单状态”这两个操作
     * 要么同时成功，要么同时失败，保证了数据的一致性。
     */
    @Transactional
    public Delivery createDelivery(Delivery delivery) {
        // 1. 检查订单是否存在且状态是否适合发货
        Order order = orderMapper.findOrderWithItemsById(delivery.getOrderId());
        if (order == null) {
            throw new RuntimeException("无法为不存在的订单发货，订单ID: " + delivery.getOrderId());
        }
        // 实际项目中可以增加更多状态检查，例如只有“已支付”的订单才能发货

        // 2. 设置配送信息
        delivery.setDispatchDate(new Date()); // 设置发货日期为当前
        delivery.setStatus("已发货"); // 初始配送状态

        // 3. 保存新的配送记录到 deliveries 表
        deliveryMapper.saveDelivery(delivery);

        // 4. 更新 orders 表中对应订单的状态
        order.setStatus("已发货");
        orderMapper.update(order); // 我们需要确保OrderMapper有update方法

        return delivery;
    }
}