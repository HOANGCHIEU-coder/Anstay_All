package com.anstay.dto;

public class RoomDTO {
    private Long id;
    private Long apartmentId;
    private String name;
    private String description;
    private Integer capacity;
    private Double price;
    private Integer maxRooms;
    private Integer maxAdults;
    private Integer maxChildren;
    private Integer discount;

    public RoomDTO() {
        this.id = id;
        this.apartmentId = apartmentId;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.maxRooms = maxRooms;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.discount = discount;
    }

    public RoomDTO(Long id, Long apartmentId, String name, String description, Integer capacity, Double price, Integer maxRooms, Integer maxAdults, Integer maxChildren, Integer discount) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.maxRooms = maxRooms;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
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
}
