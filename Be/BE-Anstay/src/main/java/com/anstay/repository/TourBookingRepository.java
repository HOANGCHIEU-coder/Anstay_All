package com.anstay.repository;

import com.anstay.entity.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourBookingRepository extends JpaRepository<TourBooking, Integer> {
    List<TourBooking> findByUserId(Long userId);

    Optional<TourBooking> findById(Long id);

    @Query("SELECT COUNT(t) FROM TourBooking t")
    long countTotalBookings();

    @Query("SELECT SUM(t.totalPrice) FROM TourBooking t")
    Double getTotalRevenue();

}
