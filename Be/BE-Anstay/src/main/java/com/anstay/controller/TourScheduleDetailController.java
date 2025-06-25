package com.anstay.controller;

import com.anstay.entity.TourScheduleDetail;
import com.anstay.service.TourScheduleDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tour-schedule-details")
public class TourScheduleDetailController {

    private final TourScheduleDetailService service;

    public TourScheduleDetailController(TourScheduleDetailService service) {
        this.service = service;
    }

    @GetMapping
    public List<TourScheduleDetail> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourScheduleDetail> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        Integer scheduleId = (Integer) body.get("schedule_id");
        if (scheduleId == null) {
            return ResponseEntity.badRequest().body("schedule_id không được để trống!");
        }

        TourScheduleDetail detail = new TourScheduleDetail();
        detail.setTimeSlot(LocalTime.parse((String) body.get("timeSlot")));
        detail.setDescription((String) body.get("description"));

        // Gọi service và truyền scheduleId
        TourScheduleDetail savedDetail = service.save(detail, scheduleId);
        return ResponseEntity.ok(savedDetail);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateScheduleDetail(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body) {

        // Lấy schedule_id từ body
        Integer scheduleId = (Integer) body.get("schedule_id");
        if (scheduleId == null) {
            return ResponseEntity.badRequest().body("schedule_id không được để trống!");
        }

        // Chuyển đổi body sang object
        TourScheduleDetail updatedDetail = new TourScheduleDetail();
        updatedDetail.setTimeSlot(LocalTime.parse((String) body.get("timeSlot")));
        updatedDetail.setDescription((String) body.get("description"));

        // Gọi service
      service.updateTourScheduleDetail(id, scheduleId, updatedDetail);
        return ResponseEntity.ok("Cập nhật thành công!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}