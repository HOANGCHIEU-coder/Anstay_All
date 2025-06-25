package com.anstay.repository;

import com.anstay.entity.Apartment;
import com.anstay.enums.Area;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

    // Tìm theo khu vực
    List<Apartment> findByArea(Area area);

    // Tìm căn hộ theo tên (chứa, không phân biệt hoa thường)
    List<Apartment> findByNameContainingIgnoreCase(String name);

    // Tìm căn hộ theo tên chính xác
    Apartment findByName(String name);

    // Tìm căn hộ đầu tiên theo tên (không phân biệt hoa thường)
    Apartment findFirstByNameIgnoreCase(String name);

    // LẤY TOÀN BỘ CĂN HỘ KÈM DANH SÁCH PHÒNG (chuẩn, duy nhất, không lỗi)
    @Override
    @EntityGraph(attributePaths = "rooms")
    List<Apartment> findAll();
}
