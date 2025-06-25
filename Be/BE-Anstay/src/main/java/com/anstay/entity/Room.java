package com.anstay.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === Cũ: trường khóa ngoại, giữ lại cho code cũ ===
    @Column(name = "apartment_id", nullable = false, insertable = false, updatable = false)
    private Long apartmentId;

    // === Mới: mapping entity, để lấy phòng theo apartment, hoặc apartment theo phòng ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @Column(nullable = false)
    private String name;

    private String description;
    private Integer capacity;
    private Double price;
    private Integer maxRooms;
    private Integer maxAdults;
    private Integer maxChildren;
    private Integer discount;

    @Column(name = "created_at")
    private Date createdAt;
    // ==== Chuẩn JPA: constructor rỗng không gán biến ====
    public Room() {}

    // ==== GETTER/SETTER GIỮ NGUYÊN ====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApartmentId() {
        // Lấy từ entity nếu có
        if (apartment != null) {
            return apartment.getId().longValue();
        }
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    // === GETTER/SETTER cho entity Apartment ===
    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
        if (apartment != null) {
            this.apartmentId = apartment.getId().longValue();
        }
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(Integer maxRooms) {
        this.maxRooms = maxRooms;
    }

    public Integer getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(Integer maxAdults) {
        this.maxAdults = maxAdults;
    }

    public Integer getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(Integer maxChildren) {
        this.maxChildren = maxChildren;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
