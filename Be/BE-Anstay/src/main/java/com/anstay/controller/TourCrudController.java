package com.anstay.controller;

import com.anstay.dto.TourDTO;
import com.anstay.service.TourCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours-crud")
public class TourCrudController {

    @Autowired
    private TourCrudService tourCrudService;

    // 🟢 Lấy danh sách tất cả Tour
    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        return ResponseEntity.ok(tourCrudService.getAllTours());
    }

    // 🟢 Lấy thông tin Tour theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Integer id) {
        TourDTO tour = tourCrudService.getTourById(id);
        return tour != null ? ResponseEntity.ok(tour) : ResponseEntity.notFound().build();
    }

    // 🟢 Thêm mới một Tour
    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {
        return ResponseEntity.ok(tourCrudService.createTour(tourDTO));
    }

    // 🟢 Cập nhật thông tin Tour
    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Integer id, @RequestBody TourDTO tourDTO) {
        TourDTO updatedTour = tourCrudService.updateTour(id, tourDTO);
        return updatedTour != null ? ResponseEntity.ok(updatedTour) : ResponseEntity.notFound().build();
    }

    // 🟢 Xóa một Tour
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Integer id) {
        tourCrudService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }
}
