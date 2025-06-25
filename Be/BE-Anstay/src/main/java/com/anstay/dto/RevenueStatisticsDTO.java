package com.anstay.dto;

import com.anstay.config.MoneySerializer;
import com.anstay.enums.BookingType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class RevenueStatisticsDTO {
    @JsonSerialize(using = MoneySerializer.class)
    private double totalRevenue;
    @JsonSerialize(using = MoneySerializer.class)
    private Double revenue;
    private String period;
    private double[] data;
    private String name;
    private long totalOrders;

    private BookingType type;
    public RevenueStatisticsDTO(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }



    public RevenueStatisticsDTO(Double revenue, String period) {
        this.revenue = revenue;
        this.period = period;
    }

    public RevenueStatisticsDTO(double[] data, String name) {
        this.data = data;
        this.name = name;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookingType getType() {
        return type;
    }

    public void setType(BookingType type) {
        this.type = type;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
