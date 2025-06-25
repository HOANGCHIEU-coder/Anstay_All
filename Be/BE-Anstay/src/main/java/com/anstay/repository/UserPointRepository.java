package com.anstay.repository;

import com.anstay.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint, Integer> {
    Optional<UserPoint> findByUserId(Integer userId);
}
