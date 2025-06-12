package com.dd.electronicbusiness.model;

import java.util.Date;
import java.util.Objects;

public class Delivery {

    private Long id;
    private Long orderId;
    private String shippingCompany;
    private String trackingNumber;
    private Date dispatchDate;
    private Date estimatedDeliveryDate;
    private Date actualDeliveryDate;
    private String status;

    // 无参构造函数
    public Delivery() {
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // --- equals, hashCode, toString ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id) && Objects.equals(orderId, delivery.orderId) && Objects.equals(shippingCompany, delivery.shippingCompany) && Objects.equals(trackingNumber, delivery.trackingNumber) && Objects.equals(dispatchDate, delivery.dispatchDate) && Objects.equals(estimatedDeliveryDate, delivery.estimatedDeliveryDate) && Objects.equals(actualDeliveryDate, delivery.actualDeliveryDate) && Objects.equals(status, delivery.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, shippingCompany, trackingNumber, dispatchDate, estimatedDeliveryDate, actualDeliveryDate, status);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", shippingCompany='" + shippingCompany + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", dispatchDate=" + dispatchDate +
                ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                ", actualDeliveryDate=" + actualDeliveryDate +
                ", status='" + status + '\'' +
                '}';
    }
}