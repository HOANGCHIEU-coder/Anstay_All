package com.anstay.repository;

import com.anstay.entity.TourImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourImageRepository extends JpaRepository<TourImage, Integer> {
    List<TourImage> findByTourId(Integer tourId);

    @Modifying
    @Transactional
    @Query("UPDATE TourImage t SET t.isFeatured = :isFeatured WHERE t.id = :id")
    int updateIsFeatured(@Param("id") Integer id, @Param("isFeatured") Boolean isFeatured);

}
