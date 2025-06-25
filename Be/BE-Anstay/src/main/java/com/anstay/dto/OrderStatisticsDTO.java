package com.anstay.dto;

public class OrderStatisticsDTO {
    private long totalOrders;

    public OrderStatisticsDTO(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
