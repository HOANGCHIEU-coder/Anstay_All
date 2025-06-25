package com.anstay.entity;

import com.anstay.enums.AptStatus;
import com.anstay.enums.Area;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private ApartmentOwner owner;

    private BigDecimal pricePerDay;
    private BigDecimal pricePerMonth;
    private BigDecimal discountPercent;
    private String description;
    private int maxAdults;
    private int maxChildren;
    private String numRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AptStatus status = AptStatus.AVAILABLE;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ApartmentImage> images;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false)
    private Area area;

    // ==== BỔ SUNG LIÊN KẾT VỚI PHÒNG ====
    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<Room> rooms;

    // ==== CHUẨN HÓA TÊN BIẾN ====
    private Integer maxBed;
    private float acreage;
    private String nameApartment;

    // ===== Constructor rỗng chuẩn JPA (không gán biến bên trong) =====
    public Apartment() {}

    // ======= GETTER/SETTER (camelCase và giữ lại getter/setter cũ dạng snake_case) =========

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ApartmentOwner getOwner() {
        return owner;
    }

    public void setOwner(ApartmentOwner owner) {
        this.owner = owner;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(int maxAdults) {
        this.maxAdults = maxAdults;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(int maxChildren) {
        this.maxChildren = maxChildren;
    }

    public String getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(String numRooms) {
        this.numRooms = numRooms;
    }

    public AptStatus getStatus() {
        return status;
    }

    public void setStatus(AptStatus status) {
        this.status = status;
    }

    public List<ApartmentImage> getImages() {
        return images;
    }

    public void setImages(List<ApartmentImage> images) {
        this.images = images;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    // ==== GETTER/SETTER mới (chuẩn camelCase) ====
    public Integer getMaxBed() {
        return maxBed;
    }

    public void setMaxBed(Integer maxBed) {
        this.maxBed = maxBed;
    }

    public float getAcreage() {
        return acreage;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }

    public String getNameApartment() {
        return nameApartment;
    }

    public void setNameApartment(String nameApartment) {
        this.nameApartment = nameApartment;
    }

    // ==== GETTER/SETTER cho rooms ====
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    // ======= GETTER/SETTER dạng snake_case vẫn giữ lại =========
    public Integer getMax_bed() {
        return maxBed;
    }
    public void setMax_bed(Integer max_bed) {
        this.maxBed = max_bed;
    }
    public String getName_apartment() {
        return nameApartment;
    }
    public void setName_apartment(String name_apartment) {
        this.nameApartment = name_apartment;
    }
}
