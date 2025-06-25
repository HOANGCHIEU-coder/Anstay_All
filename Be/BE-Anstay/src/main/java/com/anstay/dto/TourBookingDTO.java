package com.anstay.dto;

import com.anstay.entity.Tour;
import com.anstay.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TourBookingDTO {
    private Integer id;
    private Integer userId;
    private Integer tourId;
    private LocalDate checkIn;
    private BigDecimal totalPrice;
    private BookingStatus status;


    public TourBookingDTO(Integer id, Integer userId, Integer tourId, LocalDate checkIn, BigDecimal totalPrice, BookingStatus status) {
        this.id = id;
        this.userId = userId;
        this.tourId = tourId;
        this.checkIn = checkIn;
        this.totalPrice = totalPrice;
        this.status = status;

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

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }


}
