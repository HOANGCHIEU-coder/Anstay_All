package com.anstay.repository;

import com.anstay.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Integer> {

    List<PointTransaction> findByUserId(Integer userId);
}
