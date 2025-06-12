package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.CustomerMapper;
import com.dd.electronicbusiness.dao.OrderMapper;
import com.dd.electronicbusiness.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerMapper customerMapper;

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("productCount", productMapper.count());
        stats.put("orderCount", orderMapper.count());
        stats.put("customerCount", customerMapper.count());
        return stats;
    }
}