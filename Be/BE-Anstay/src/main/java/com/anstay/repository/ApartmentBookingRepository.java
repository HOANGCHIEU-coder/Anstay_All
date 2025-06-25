package com.anstay.repository;

import com.anstay.entity.ApartmentBooking;
import com.anstay.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ApartmentBookingRepository extends JpaRepository<ApartmentBooking, Integer> {

    List<ApartmentBooking> findByUserId(Integer userId);
    List<ApartmentBooking> findByApartmentId(Integer apartmentId);
    List<ApartmentBooking> findByRoomId(Long roomId);
    List<ApartmentBooking> findByStatus(BookingStatus status);

    // Booking còn hiệu lực cho 1 phòng và khoảng ngày (check trùng lịch)
    @Query("""
        SELECT ab FROM ApartmentBooking ab
        WHERE ab.room.id = :roomId
          AND ab.status IN :statuses
          AND (ab.checkIn <= :checkOut AND ab.checkOut >= :checkIn)
    """)
    List<ApartmentBooking> findActiveBookingsByRoomAndDateRange(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("statuses") List<BookingStatus> statuses
    );

    // Booking HOLD đã hết hạn (cho cron job tự động huỷ giữ phòng)
    @Query("""
    SELECT ab FROM ApartmentBooking ab
    WHERE ab.status = com.anstay.enums.BookingStatus.HOLD
      AND ab.createdAt < :expiredTime
    """)
    List<ApartmentBooking> findExpiredHoldBookings(@Param("expiredTime") LocalDateTime expiredTime);

    // Đếm tổng booking (toàn hệ thống)
    @Query("SELECT COUNT(a) FROM ApartmentBooking a")
    long countTotalBookings();

    // Tổng doanh thu booking
    @Query("SELECT SUM(a.totalPrice) FROM ApartmentBooking a")
    Double getTotalRevenue();

    // ===== BỔ SUNG: Lấy toàn bộ booking JOIN Apartment để lấy thông tin area =====
    @Query("""
        SELECT ab FROM ApartmentBooking ab
        JOIN FETCH ab.apartment a
    """)
    List<ApartmentBooking> findAllWithApartment();

    // ===== BỔ SUNG: Lấy toàn bộ booking theo status và JOIN Apartment =====
    @Query("""
    SELECT ab FROM ApartmentBooking ab
    JOIN FETCH ab.apartment a
    WHERE ab.status = :status
""")
    List<ApartmentBooking> findAllByStatusWithApartment(@Param("status") BookingStatus status);

    @Query("SELECT COUNT(ab) FROM ApartmentBooking ab")
    long countAll();

    // Đếm số đơn hàng thành công (status = 'CONFIRMED')
    long countByStatus(BookingStatus status);

    // Đếm số đơn hàng bị hủy (CANCELLED) và hết hạn (EXPIRED)
    @Query("SELECT COUNT(b) FROM ApartmentBooking b WHERE b.status IN :statuses")
    long countByStatusIn(List<BookingStatus> statuses);

}
