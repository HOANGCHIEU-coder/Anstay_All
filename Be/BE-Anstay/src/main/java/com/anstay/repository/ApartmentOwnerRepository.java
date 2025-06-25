package com.anstay.repository;

import com.anstay.entity.ApartmentOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentOwnerRepository extends JpaRepository<ApartmentOwner, Integer> {
}
