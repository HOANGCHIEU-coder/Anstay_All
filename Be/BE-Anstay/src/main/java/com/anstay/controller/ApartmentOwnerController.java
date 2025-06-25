package com.anstay.controller;

import com.anstay.dto.ApartmentOwnerDTO;
import com.anstay.service.ApartmentOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartment-owners")
public class ApartmentOwnerController {

    @Autowired
    private ApartmentOwnerService apartmentOwnerService;

    // Lấy tất cả chủ căn hộ
    @GetMapping
    public ResponseEntity<List<ApartmentOwnerDTO>> getAllOwners() {
        return ResponseEntity.ok(apartmentOwnerService.getAllOwners());
    }

    // Lấy chủ căn hộ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentOwnerDTO> getOwnerById(@PathVariable Integer id) {
        ApartmentOwnerDTO ownerDTO = apartmentOwnerService.getOwnerById(id);
        if (ownerDTO != null) {
            return ResponseEntity.ok(ownerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // Tạo mới chủ căn hộ
    @PostMapping
    public ResponseEntity<ApartmentOwnerDTO> createOwner(@RequestBody ApartmentOwnerDTO ownerDTO) {
        ApartmentOwnerDTO savedOwner = apartmentOwnerService.createOwner(ownerDTO);
        return ResponseEntity.ok(savedOwner);
    }

    // Cập nhật thông tin chủ căn hộ
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentOwnerDTO> updateOwner(@PathVariable Integer id, @RequestBody ApartmentOwnerDTO ownerDTO) {
        ApartmentOwnerDTO updatedOwner = apartmentOwnerService.updateOwner(id, ownerDTO);
        if (updatedOwner != null) {
            return ResponseEntity.ok(updatedOwner);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa chủ căn hộ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        boolean deleted = apartmentOwnerService.deleteOwner(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
