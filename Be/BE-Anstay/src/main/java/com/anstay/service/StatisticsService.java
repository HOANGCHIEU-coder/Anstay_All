package com.anstay.service;

import com.anstay.dto.OrderStatisticsDTO;
import com.anstay.dto.RevenueStatisticsDTO;
import com.anstay.dto.UserStatisticsDTO;

import java.util.List;

public interface StatisticsService {

    UserStatisticsDTO getTotalUsers();
    OrderStatisticsDTO getTotalOrders();
    RevenueStatisticsDTO getTotalRevenue();
    List<RevenueStatisticsDTO> getMonthlyRevenue();
    List<RevenueStatisticsDTO> getRevenueByAreaAndType();
}
