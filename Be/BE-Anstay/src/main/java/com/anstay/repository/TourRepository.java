package com.anstay.repository;

import com.anstay.entity.Tour;
import com.anstay.enums.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    List<Tour> findByArea(Area area);
}
