package com.anstay.repository;

import com.anstay.entity.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {

    List<TourSchedule> findByTourId(Integer tourId);
}
