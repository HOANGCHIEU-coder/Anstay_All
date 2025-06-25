package com.anstay.repository;

import com.anstay.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCrudRepository extends JpaRepository<Tour, Integer> {
}
