package com.anstay.service;

import com.anstay.entity.ApartmentBooking;
import com.anstay.enums.BookingStatus;
import com.anstay.repository.ApartmentBookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingCleanupScheduler {
    @Autowired
    private ApartmentBookingRepository bookingRepo;

    // Chạy mỗi 1 phút
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanupExpiredHoldBookings() {
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(10);
        List<ApartmentBooking> expiredBookings = bookingRepo.findExpiredHoldBookings(expiredTime);
        for (ApartmentBooking booking : expiredBookings) {
            booking.setStatus(BookingStatus.EXPIRED); // hoặc bookingRepo.delete(booking)
        }
        bookingRepo.saveAll(expiredBookings);
    }
}
