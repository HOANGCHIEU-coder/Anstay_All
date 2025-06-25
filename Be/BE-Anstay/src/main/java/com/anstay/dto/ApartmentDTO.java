package com.anstay.dto;

import com.anstay.entity.ApartmentOwner;
import com.anstay.enums.AptStatus;
import com.anstay.enums.Area;

import java.math.BigDecimal;
import java.util.List;

public class ApartmentDTO {
    private Integer id;
    private String name;
    private String location;
    private Integer ownerId;
    private BigDecimal pricePerDay;
    private BigDecimal pricePerMonth;
    private BigDecimal discountPercent;
    private String description;
    private int maxAdults;
    private int maxChildren;
    private String numRooms;
    private AptStatus status;
    private List<ApartmentOwnerDTO> owners;
    private List<ApartmentImageDTO> images;
    private Area area;
    private Integer max_bed;
    private float acreage;
    private String name_apartment;


    public ApartmentDTO(Integer id, String name, String location, Integer ownerId, BigDecimal pricePerDay, BigDecimal pricePerMonth, BigDecimal discountPercent, String description, int maxAdults, int maxChildren, String numRooms, AptStatus status, List<ApartmentOwnerDTO> owners, List<ApartmentImageDTO> images, Area area, Integer max_bed, float acreage, String name_apartment) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.ownerId = ownerId;
        this.pricePerDay = pricePerDay;
        this.pricePerMonth = pricePerMonth;
        this.discountPercent = discountPercent;
        this.description = description;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.numRooms = numRooms;
        this.status = status;
        this.owners = owners;
        this.images = images;
        this.area = area;
        this.max_bed = max_bed;
        this.acreage = acreage;
        this.name_apartment = name_apartment;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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

    public List<ApartmentOwnerDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<ApartmentOwnerDTO> owners) {
        this.owners = owners;
    }

    public List<ApartmentImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ApartmentImageDTO> images) {
        this.images = images;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Integer getMax_bed() {
        return max_bed;
    }

    public void setMax_bed(Integer max_bed) {
        this.max_bed = max_bed;
    }

    public float getAcreage() {
        return acreage;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }

    public String getName_apartment() {
        return name_apartment;
    }

    public void setName_apartment(String name_apartment) {
        this.name_apartment = name_apartment;
    }
}
