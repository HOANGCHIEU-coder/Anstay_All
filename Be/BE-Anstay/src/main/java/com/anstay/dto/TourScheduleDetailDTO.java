package com.anstay.dto;

import java.time.LocalTime;

public class TourScheduleDetailDTO {
    private Integer id;
    private Integer schedule_id;
    private LocalTime timeSlot;
    private String description;

    public TourScheduleDetailDTO(Integer id, LocalTime timeSlot, String description) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
