package com.anstay.repository;

import com.anstay.entity.ApartmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentImageRepository extends JpaRepository<ApartmentImage, Integer> {
    // Tìm danh sách ảnh có cùng apartment_id
    List<ApartmentImage> findByApartment_Id(Integer apartmentId);
}
