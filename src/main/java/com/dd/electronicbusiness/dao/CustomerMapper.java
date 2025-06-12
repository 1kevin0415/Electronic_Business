package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CustomerMapper {

    /**
     * 查询所有客户
     * @return 客户列表
     */
    List<Customer> findAllCustomers();

    /**
     * 新增一个客户
     * @param customer 客户对象
     */
    void saveCustomer(Customer customer);

    /**
     * 根据ID查找客户
     * @param id 客户ID
     * @return 客户对象
     */
    Customer findCustomerById(Long id);

}