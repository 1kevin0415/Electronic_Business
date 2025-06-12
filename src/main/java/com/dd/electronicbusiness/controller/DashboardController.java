package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.model.ApiResponse; // 1. 导入 ApiResponse 类
import com.dd.electronicbusiness.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> getStats() { // 2. 修改返回类型
        Map<String, Long> stats = dashboardService.getStats();
        return ApiResponse.success(stats); // 3. 将结果用 ApiResponse.success() 包裹
    }
}
