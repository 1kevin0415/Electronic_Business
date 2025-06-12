package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.CustomerMapper;
import com.dd.electronicbusiness.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomers() {
        return customerMapper.findAllCustomers();
    }

    public Customer getCustomerById(Long id) {
        return customerMapper.findCustomerById(id);
    }

    public Customer createCustomer(Customer customer) {
        // 设置默认的注册日期为当前时间
        customer.setRegistrationDate(new Date());
        customerMapper.saveCustomer(customer);
        return customer;
    }
}