package com.anstay.repository;

import com.anstay.entity.User;
import com.anstay.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    List<User> findByRole(Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role IN ('SUPER_ADMIN', 'ADMIN')")
    long countAdminsAndSuperAdmins();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role IN ('SUPER_ADMIN', 'ADMIN') AND u.createdAt > :date")
    long countNewAdminsAndSuperAdminsLastMonth(Date date);
}
