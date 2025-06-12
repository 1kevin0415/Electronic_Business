package com.dd.electronicbusiness.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.io.Serializable; // 确保导入

// 为了将来可能对Order对象进行缓存，我们最好也实现Serializable接口
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long customerId;
    private Date orderDate;
    private BigDecimal totalPrice;
    private String status;
    private String shippingAddress;

    // --- 这是关键的修改点 ---
    // 列表的泛型从 OrderItem 修改为 OrderItemDTO
    private List<OrderItemDTO> orderItems;

    public Order() {
    }

    // --- Getters and Setters (同样需要修改返回和参数类型) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    // --- get/set OrderItems 的类型也必须是 List<OrderItemDTO> ---
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customerId, order.customerId) && Objects.equals(orderDate, order.orderDate) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(status, order.status) && Objects.equals(shippingAddress, order.shippingAddress) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, orderDate, totalPrice, status, shippingAddress, orderItems);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}