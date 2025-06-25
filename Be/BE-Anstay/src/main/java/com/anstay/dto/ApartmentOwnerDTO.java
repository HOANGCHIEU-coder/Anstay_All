package com.anstay.dto;

import java.util.List;

public class ApartmentOwnerDTO {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;



    // Constructors
    public ApartmentOwnerDTO() {}

    public ApartmentOwnerDTO(Integer id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public ApartmentOwnerDTO( String name,String phone,String email,String address) {

        this.name = name;
        this.phone =phone;
        this.email = email;
        this.address = address;
    }

    // Getters & Setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
