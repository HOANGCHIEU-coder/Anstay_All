package com.anstay.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tour_schedule_details")
public class TourScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private TourSchedule schedule;

    @Column(name = "time_slot", nullable = false)
    private LocalTime timeSlot;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    public TourScheduleDetail() {
    }

    public TourScheduleDetail(Integer id, TourSchedule schedule, LocalTime timeSlot, String description) {
        this.id = id;
        this.schedule = schedule;
        this.timeSlot = timeSlot;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TourSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(TourSchedule schedule) {
        this.schedule = schedule;
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
