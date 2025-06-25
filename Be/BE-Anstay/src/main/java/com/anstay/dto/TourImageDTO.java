package com.anstay.dto;

public class TourImageDTO {
    private Integer id;
    private Integer tourId;
    private String imageUrl;
    private Boolean isFeatured;

    public TourImageDTO(Integer id, Integer tourId, String imageUrl, Boolean isFeatured) {
        this.id = id;
        this.tourId = tourId;
        this.imageUrl = imageUrl;
        this.isFeatured = isFeatured;
    }
    public TourImageDTO() {}
    public TourImageDTO(Integer id, Integer id1, String imageUrl, Object featured) {
        this.id = id;
        this.tourId = id1;
        this.imageUrl = imageUrl;
        this.isFeatured = (Boolean) featured;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean featured) {
        isFeatured = featured;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
}
