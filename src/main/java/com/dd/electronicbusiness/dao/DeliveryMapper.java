package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Delivery;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DeliveryMapper {

    /**
     * 查询所有配送信息
     * @return 配送列表
     */
    List<Delivery> findAllDeliveries();

    /**
     * 新增一个配送信息
     * @param delivery 配送对象
     */
    void saveDelivery(Delivery delivery);

    /**
     * 根据ID查找配送信息
     * @param id 配送ID
     * @return 配送对象
     */
    Delivery findDeliveryById(Long id);

    /**
     * 根据订单ID查找配送信息
     * @param orderId 订单ID
     * @return 配送对象
     */
    Delivery findDeliveryByOrderId(Long orderId);
}