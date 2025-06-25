package com.anstay.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "apartment_images")
public class ApartmentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = true)
    private Apartment apartment;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_featured", nullable = false)
    private boolean isFeatured = false;

    // Constructors
    public ApartmentImage() {}

    public ApartmentImage(Apartment apartment, String imageUrl, boolean isFeatured) {
        this.apartment = apartment;
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
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
