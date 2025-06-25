package com.anstay.controller;


import com.anstay.dto.OrderStatisticsDTO;
import com.anstay.dto.RevenueStatisticsDTO;
import com.anstay.dto.UserStatisticsDTO;
import com.anstay.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/users")
    public UserStatisticsDTO getTotalUsers() {
        return statisticsService.getTotalUsers();
    }

    @GetMapping("/orders")
    public OrderStatisticsDTO getTotalOrders() {
        return statisticsService.getTotalOrders();
    }

    @GetMapping("/revenue")
    public RevenueStatisticsDTO getTotalRevenue() {
        return statisticsService.getTotalRevenue();
    }

    @GetMapping("/monthly-revenue")
    public List<RevenueStatisticsDTO> getMonthlyRevenue() {
        return statisticsService.getMonthlyRevenue();
    }

    @GetMapping("/revenue-by-area")
    public List<RevenueStatisticsDTO> getRevenueByArea() {
        return statisticsService.getRevenueByAreaAndType();
    }
}
