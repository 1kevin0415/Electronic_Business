package com.dd.electronicbusiness.model;

import java.io.Serializable;
import java.util.Objects;

// 1. 确保它也实现了 Serializable 接口
public class OrderDTO extends Order implements Serializable {

    // 2. 添加一个 serialVersionUID
    private static final long serialVersionUID = 1L;

    private String customerName;

    // 3. 添加无参构造函数
    public OrderDTO() {
        super(); // 调用父类的构造函数
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // 4. 重写 equals 和 hashCode 方法，将 customerName 也包含在内
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // 先比较父类的字段
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(customerName, orderDTO.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerName); // 将父类和子类的字段一起计算哈希值
    }

    // 5. 重写 toString 方法，打印出更详细的信息
    @Override
    public String toString() {
        // 在父类toString()的基础上，追加customerName
        return "OrderDTO{" +
                "order=" + super.toString() +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}