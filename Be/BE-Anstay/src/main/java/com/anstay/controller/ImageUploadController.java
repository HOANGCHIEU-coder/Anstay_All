package com.anstay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // Đường dẫn thư mục tuyệt đối trên server
        String uploadDir = "/home/uploads/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Tạo thư mục nếu chưa tồn tại
        Files.createDirectories(filePath.getParent());
        // Lưu file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Trả về url cho FE (FE tự dùng url này để lưu vào DB nếu muốn)
        String url = "/uploads/" + fileName;
        return ResponseEntity.ok(new UploadResponse(fileName, url));
    }

    // Đối tượng trả về (có thể trả đơn giản chỉ url cũng được)
    static class UploadResponse {
        public String fileName;
        public String url;
        public UploadResponse(String fileName, String url) {
            this.fileName = fileName;
            this.url = url;
        }
    }
}
