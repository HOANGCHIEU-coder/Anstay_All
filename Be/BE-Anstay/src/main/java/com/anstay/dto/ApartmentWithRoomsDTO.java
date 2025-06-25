package com.anstay.dto;

import com.anstay.enums.Area;
import java.util.List;

public class ApartmentWithRoomsDTO {
    private Integer id;
    private String name;
    private Area area; // hoặc String area nếu FE chỉ cần chuỗi
    private List<RoomSimpleDTO> rooms;

    public ApartmentWithRoomsDTO() {}

    public ApartmentWithRoomsDTO(Integer id, String name, Area area, List<RoomSimpleDTO> rooms) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.rooms = rooms;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<RoomSimpleDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomSimpleDTO> rooms) {
        this.rooms = rooms;
    }
}
