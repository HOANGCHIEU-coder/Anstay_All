package com.anstay.controller;

import com.anstay.dto.ApartmentImageDTO;
import com.anstay.service.ApartmentImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/apartment-images")
public class ApartmentImageController {

    @Autowired
    private ApartmentImageService apartmentImageService;

    // Lấy tất cả ảnh theo apartmentId
    @GetMapping
    public ResponseEntity<List<ApartmentImageDTO>> getImagesByApartmentId(@RequestParam("apartmentId") Integer apartmentId) {
        List<ApartmentImageDTO> images = apartmentImageService.getImagesByApartmentId(apartmentId);
        return images.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(images);
    }

    // Lấy 1 ảnh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentImageDTO> getImageById(@PathVariable Integer id) {
        ApartmentImageDTO image = apartmentImageService.getImageById(id);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    // 🟢 Upload 1 hoặc nhiều ảnh, lưu file vào thư mục server
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<List<ApartmentImageDTO>> uploadImages(
            @RequestParam("images") List<MultipartFile> files,
            @RequestParam("apartmentId") Integer apartmentId
    ) throws IOException {
        String uploadDir = "/home/uploads/";
        List<ApartmentImageDTO> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String url = "https://anstay.com.vn/uploads/" + fileName;

            // Tạo DTO lưu vào DB
            ApartmentImageDTO imageDTO = new ApartmentImageDTO();
            imageDTO.setApartmentId(apartmentId);
            imageDTO.setImageUrl(url);
            // Nếu có các trường khác (isFeatured, desc...), set thêm ở đây

            ApartmentImageDTO savedImage = apartmentImageService.createImage(imageDTO);
            savedImages.add(savedImage);
        }
        return ResponseEntity.ok(savedImages);
    }

    // 🟢 Cập nhật ảnh (không cập nhật file, chỉ update thông tin khác)
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentImageDTO> updateImage(@PathVariable Integer id, @RequestBody ApartmentImageDTO imageDTO) {
        ApartmentImageDTO updatedImage = apartmentImageService.updateImage(id, imageDTO);
        return updatedImage != null ? ResponseEntity.ok(updatedImage) : ResponseEntity.notFound().build();
    }

    // 🟢 Xóa ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        return apartmentImageService.deleteImage(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
