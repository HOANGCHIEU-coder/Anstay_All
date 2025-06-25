package com.anstay.repository;

import com.anstay.entity.TourScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourScheduleDetailRepository extends JpaRepository<TourScheduleDetail, Integer> {
    List<TourScheduleDetail> findByScheduleId(Integer scheduleId);
    void deleteByScheduleId(Integer scheduleId);
}
