package com.anstay.dto;

public class UserStatisticsDTO {

    private long totalUsers;

    public UserStatisticsDTO(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }
}
