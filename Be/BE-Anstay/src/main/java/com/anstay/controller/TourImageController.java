package com.anstay.controller;

import com.anstay.dto.TourImageDTO;
import com.anstay.service.TourImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tour-images")
public class TourImageController {

    @Autowired
    private TourImageService tourImageService;

    // 🟢 Lấy danh sách ảnh theo tour_id
    @GetMapping("/{tourId}")
    public ResponseEntity<List<TourImageDTO>> getImagesByTourId(@PathVariable Integer tourId) {
        List<TourImageDTO> images = tourImageService.getImagesByTourId(tourId);
        return ResponseEntity.ok(images);
    }

    // 🟢 Thêm ảnh bằng file upload (upload nhiều ảnh 1 lần)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<List<TourImageDTO>> uploadTourImages(
            @RequestParam("images") List<MultipartFile> files,
            @RequestParam("tour_id") Integer tourId
    ) throws IOException {
        String uploadDir = "/home/uploads/";
        List<TourImageDTO> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String url = "https://anstay.com.vn/uploads/" + fileName;

            // Tạo đối tượng DTO để lưu DB và trả về FE (tùy thuộc logic service của bạn)
            TourImageDTO tourImageDTO = new TourImageDTO();
            tourImageDTO.setTourId(tourId);
            tourImageDTO.setImageUrl(url);
            // Nếu có trường khác thì set thêm (isFeatured, ...)

            TourImageDTO savedImage = tourImageService.addTourImage(tourImageDTO);
            savedImages.add(savedImage);
        }
        return ResponseEntity.ok(savedImages);
    }

    // 🟢 Thêm ảnh bằng DTO (nếu bạn vẫn muốn truyền URL thủ công)
    @PostMapping(value = "/add-by-url", consumes = "application/json")
    public ResponseEntity<TourImageDTO> addTourImage(@RequestBody TourImageDTO tourImageDTO) {
        TourImageDTO savedImage = tourImageService.addTourImage(tourImageDTO);
        if (savedImage != null) {
            return ResponseEntity.ok(savedImage);
        }
        return ResponseEntity.badRequest().build();
    }

    // 🟢 Xóa ảnh theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourImage(@PathVariable Integer id) {
        boolean isDeleted = tourImageService.deleteTourImage(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // 🟢 Đánh dấu ảnh nổi bật
    @PatchMapping("/{id}/isFeatured")
    public ResponseEntity<Void> toggleIsFeatured(@PathVariable Integer id) {
        boolean isUpdated = tourImageService.toggleIsFeatured(id);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
