package com.anstay.controller;

import com.anstay.dto.TourBookingDTO;
import com.anstay.enums.BookingStatus;
import com.anstay.service.TourBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-bookings")
public class TourBookingController {
    private final TourBookingService tourBookingService;

    public TourBookingController(TourBookingService tourBookingService) {
        this.tourBookingService = tourBookingService;
    }

    @PostMapping
    public ResponseEntity<TourBookingDTO> createBooking(@RequestBody TourBookingDTO request) {
        return ResponseEntity.ok(tourBookingService.createBooking(request));
    }

    @GetMapping
    public ResponseEntity<List<TourBookingDTO>> getAllBookings() {
        return ResponseEntity.ok(tourBookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourBookingDTO> getBookingById(@PathVariable Long id) {
        return tourBookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TourBookingDTO> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return tourBookingService.updateBookingStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        if (tourBookingService.deleteBooking(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
