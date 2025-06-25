package com.anstay.controller;

import com.anstay.dto.ApartmentBookingDTO;
import com.anstay.service.ApartmentBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/apartment-bookings")
public class ApartmentBookingController {

    @Autowired
    private ApartmentBookingService bookingService;

    // Lấy tất cả booking
    @GetMapping
    public ResponseEntity<List<ApartmentBookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Lấy booking theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentBookingDTO> getBookingById(@PathVariable Integer id) {
        ApartmentBookingDTO booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    // Lọc booking theo userId
    @GetMapping("/by-user")
    public ResponseEntity<List<ApartmentBookingDTO>> getByUser(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    // Lọc booking theo roomId
    @GetMapping("/by-room")
    public ResponseEntity<List<ApartmentBookingDTO>> getByRoom(@RequestParam Long roomId) {
        return ResponseEntity.ok(bookingService.getBookingsByRoomId(roomId));
    }

    // Lọc booking theo status
    @GetMapping("/by-status")
    public ResponseEntity<List<ApartmentBookingDTO>> getByStatus(@RequestParam String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    // API giữ phòng (booking mới)
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody ApartmentBookingDTO bookingDTO) {
        ApartmentBookingDTO savedBooking = bookingService.createBooking(bookingDTO);
        if (savedBooking != null) {
            return ResponseEntity.ok(savedBooking);
        } else {
            return ResponseEntity.badRequest().body("Không thể giữ phòng: phòng đã được giữ hoặc thông tin không hợp lệ!");
        }
    }

    // Cập nhật booking
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentBookingDTO> updateBooking(@PathVariable Integer id, @RequestBody ApartmentBookingDTO bookingDTO) {
        ApartmentBookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.notFound().build();
    }

    // Xóa booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        boolean deleted = bookingService.deleteBooking(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Check phòng trống
    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut
    ) {
        boolean available = bookingService.isRoomAvailable(
                roomId,
                LocalDate.parse(checkIn),
                LocalDate.parse(checkOut)
        );
        return ResponseEntity.ok(available);
    }

    // ===== API group by area (QUAN TRỌNG) =====
    @GetMapping("/by-area")
    public ResponseEntity<?> getAllHoldBookingsGroupedByArea() {
        return ResponseEntity.ok(bookingService.getAllHoldBookingsGroupedByArea());
    }

}
