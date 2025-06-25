package com.anstay.controller;

import com.anstay.dto.TourScheduleDTO;
import com.anstay.service.TourScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-schedules")
public class TourScheduleController {

    @Autowired
    private TourScheduleService tourScheduleService;

    // 🟢 Lấy danh sách lịch trình theo tour_id
    @GetMapping("/{tourId}")
    public ResponseEntity<List<TourScheduleDTO>> getSchedulesByTourId(@PathVariable Integer tourId) {
        List<TourScheduleDTO> schedules = tourScheduleService.getSchedulesByTourId(tourId);
        return ResponseEntity.ok(schedules);
    }

    // 🟢 Thêm lịch trình mới
    @PostMapping
    public ResponseEntity<TourScheduleDTO> addTourSchedule(@RequestBody TourScheduleDTO dto) {
        TourScheduleDTO savedSchedule = tourScheduleService.addTourSchedule(dto);
        if (savedSchedule != null) {
            return ResponseEntity.ok(savedSchedule);
        }
        return ResponseEntity.badRequest().build();
    }

    // 🟢 Xóa lịch trình theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourSchedule(@PathVariable Integer id) {
        boolean isDeleted = tourScheduleService.deleteTourSchedule(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourScheduleDTO> updateTourSchedule(@PathVariable Integer id, @RequestBody TourScheduleDTO dto) {
        TourScheduleDTO updatedSchedule = tourScheduleService.updateTourSchedule(id, dto);
        return updatedSchedule != null ? ResponseEntity.ok(updatedSchedule) : ResponseEntity.notFound().build();
    }
}
