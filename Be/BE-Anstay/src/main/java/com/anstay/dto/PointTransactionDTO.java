package com.anstay.dto;

import com.anstay.enums.TransactionType;

import java.time.LocalDateTime;

public class PointTransactionDTO {
    private Integer userId;
    private TransactionType transactionType;
    private Integer points;
    private String description;
    private LocalDateTime createdAt;

    public PointTransactionDTO(Integer userId, TransactionType transactionType, Integer points, String description, LocalDateTime createdAt) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.points = points;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
