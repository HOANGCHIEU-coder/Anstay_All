package com.anstay.dto;

import java.time.LocalDateTime;

public class UserPointDTO {
    private Integer id;
    private Integer userId;
    private Integer points;
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public UserPointDTO(Integer id, Integer userId, Integer points, LocalDateTime lastUpdated) {
        this.id = id;
        this.userId = userId;
        this.points = points;
        this.lastUpdated = lastUpdated;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
