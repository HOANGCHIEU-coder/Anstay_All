package com.anstay.dto;

import com.anstay.entity.Tour;
import com.anstay.enums.Area;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TourDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;

    private Integer durationDays;
    private BigDecimal discountPercent;
    private LocalDateTime createdAt;

    private List<TourScheduleDTO> schedules; // 🟢 Danh sách lịch trình
    private List<TourImageDTO> images;

    private Area area;
    private String transportation;
    private String hotel;


    public TourDTO() {
    }

    public TourDTO(Tour tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.description = tour.getDescription();
        this.price = tour.getPrice();
        this.durationDays = tour.getDurationDays();
        this.discountPercent = tour.getDiscountPercent();
        this.createdAt = tour.getCreatedAt();
    }

    public TourDTO(Integer id, String name, String description, BigDecimal price, Integer durationDays, BigDecimal discountPercent, LocalDateTime createdAt, List<TourScheduleDTO> schedules, List<TourImageDTO> images, Area area, String transportation, String hotel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
        this.discountPercent = discountPercent;
        this.createdAt = createdAt;
        this.schedules = schedules;
        this.images = images;
        this.area = area;
        this.transportation = transportation;
        this.hotel = hotel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TourScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TourScheduleDTO> schedules) {
        this.schedules = schedules;
    }

    public List<TourImageDTO> getImages() {
        return images;
    }

    public void setImages(List<TourImageDTO> images) {
        this.images = images;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }
}
