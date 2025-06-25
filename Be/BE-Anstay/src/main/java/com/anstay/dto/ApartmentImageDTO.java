package com.anstay.dto;

public class ApartmentImageDTO {
    private Integer id;
    private Integer apartmentId;
    private String imageUrl;
    private boolean isFeatured;

    // Constructors
    public ApartmentImageDTO() {}

    public ApartmentImageDTO(Integer id, Integer apartmentId, String imageUrl, boolean isFeatured) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.imageUrl = imageUrl;
        this.isFeatured = isFeatured;
    }

    // Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }
}
