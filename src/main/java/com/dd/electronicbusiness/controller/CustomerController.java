package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.ApiResponse;
import com.dd.electronicbusiness.model.Customer;
import com.dd.electronicbusiness.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {
        return ApiResponse.success(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ApiResponse<Customer> getCustomerById(@PathVariable Long id) {
        return ApiResponse.success(customerService.getCustomerById(id));
    }

    @PostMapping
    public ApiResponse<Customer> createCustomer(@RequestBody Customer customer) {
        return ApiResponse.success(customerService.createCustomer(customer));
    }
}
