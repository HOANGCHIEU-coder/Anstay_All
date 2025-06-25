package com.anstay.repository;

import com.anstay.entity.Payment;
import com.anstay.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByUserId(Integer userId);

    List<Payment> findByStatus(PaymentStatus status);

    // Tìm theo trạng thái đơn lẻ
    List<Payment> findByStatus(String status);

    // Tìm theo nhiều trạng thái
    List<Payment> findByStatusIn(List<PaymentStatus> statuses);

    //Tìm theo transactionId
    Optional<Payment> findByTransactionId(String transactionId);

    @Query("SELECT SUM(p.amount) FROM Payment p")
    Double getTotalRevenue();

    @Query(value = "SELECT a.area, " +
            "DATE_FORMAT(p.created_at, '%Y-%m') as month, " +
            "COALESCE(SUM(p.amount), 0) as revenue " +
            "FROM payments p " +
            "INNER JOIN apartment_bookings ab ON p.booking_id = ab.id " +
            "INNER JOIN apartments a ON ab.apartment_id = a.id " +
            "WHERE p.status = 'COMPLETED' " +
            "AND p.booking_type = 'APARTMENT' " +
            "GROUP BY a.area, month " +
            "ORDER BY a.area, month", nativeQuery = true)
    List<Object[]> getMonthlyRevenueByApartmentArea();

    @Query(value = "SELECT t.area, " +
            "DATE_FORMAT(p.created_at, '%Y-%m') as month, " +
            "COALESCE(SUM(p.amount), 0) as revenue " +
            "FROM payments p " +
            "INNER JOIN tour_bookings tb ON p.booking_id = tb.id " +
            "INNER JOIN tours t ON tb.tour_id = t.id " +
            "WHERE p.status = 'COMPLETED' " +
            "AND p.booking_type = 'TOUR' " +
            "GROUP BY t.area, month " +
            "ORDER BY t.area, month", nativeQuery = true)
    List<Object[]> getMonthlyRevenueByTourArea();

    @Query(value = """
    SELECT t.area, SUM(p.amount), COUNT(p.id)
    FROM payments p
    INNER JOIN tour_bookings tb ON p.booking_id = tb.id
    INNER JOIN tours t ON tb.tour_id = t.id
    WHERE p.booking_type = 'TOUR'
    AND p.status = 'COMPLETED'
    GROUP BY t.area
    """, nativeQuery = true)
    List<Object[]> getRevenueAndOrdersByTourArea();

    @Query(value = """
    SELECT a.area, SUM(p.amount), COUNT(p.id)
    FROM payments p
    INNER JOIN apartment_bookings ab ON p.booking_id = ab.id
    INNER JOIN apartments a ON ab.apartment_id = a.id
    WHERE p.booking_type = 'APARTMENT'
    AND p.status = 'COMPLETED'
    GROUP BY a.area
    """, nativeQuery = true)
    List<Object[]> getRevenueAndOrdersByApartmentArea();

    @Query(value = "SELECT a.area, SUM(p.amount) " +
            "FROM payments p " +
            "INNER JOIN apartment_bookings ab ON p.booking_id = ab.id " +
            "INNER JOIN apartments a ON ab.apartment_id = a.id " +
            "WHERE p.status = 'COMPLETED' " +
            "AND p.booking_type = 'APARTMENT' " +
            "GROUP BY a.area", nativeQuery = true)
    List<Object[]> getRevenueByApartmentArea();

    @Query(value = "SELECT t.area, SUM(p.amount) " +
            "FROM payments p " +
            "INNER JOIN tour_bookings tb ON p.booking_id = tb.id " +
            "INNER JOIN tours t ON tb.tour_id = t.id " +
            "WHERE p.status = 'COMPLETED' " +
            "AND p.booking_type = 'TOUR' " +
            "GROUP BY t.area", nativeQuery = true)
    List<Object[]> getRevenueByTourArea();

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PAID'")
    Long sumAmountForPaidStatus();

    @Query("SELECT p FROM Payment p WHERE p.status = com.anstay.enums.PaymentStatus.PENDING")
    List<Payment> findPendingPayments();

    @Query(value = """
    SELECT p.id, p.amount, p.status, ab.check_in, ab.check_out
    FROM payments p
    JOIN apartment_bookings ab ON p.booking_id = ab.id
    WHERE p.booking_type = 'APARTMENT'
    """, nativeQuery = true)
    List<Object[]> findPaymentsWithCheckInOut();

    List<Payment> findByStatusInOrderByCreatedAtDesc(List<String> statuses);

    List<Payment> findByStatusOrderByCreatedAtDesc(String upperCase);
}
