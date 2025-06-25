package com.anstay.controller;

import com.anstay.dto.ApartmentContactDTO;
import com.anstay.dto.ApartmentDTO;
import com.anstay.dto.ApartmentWithRoomsDTO; // <-- THÊM IMPORT NÀY
import com.anstay.entity.Apartment;
import com.anstay.enums.Area;
import com.anstay.repository.ApartmentRepository;
import com.anstay.service.ApartmentService;
import com.anstay.service.ContactService;
import com.anstay.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    // Lấy tất cả căn hộ
    @GetMapping
    public ResponseEntity<List<ApartmentDTO>> getAllApartments() {
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }

    // Lấy căn hộ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDTO> getApartmentById(@PathVariable Integer id) {
        ApartmentDTO apartment = apartmentService.getApartmentById(id);
        return apartment != null ? ResponseEntity.ok(apartment) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApartmentDTO>> searchApartmentsByName(@RequestParam String name) {
        List<ApartmentDTO> result = apartmentService.searchApartmentsByName(name);
        return ResponseEntity.ok(result);
    }

    // Tạo mới căn hộ
    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentDTO apartmentDTO) {
        ApartmentDTO savedApartment = apartmentService.createApartment(apartmentDTO);
        return savedApartment != null ? ResponseEntity.ok(savedApartment) : ResponseEntity.badRequest().build();
    }

    // Cập nhật căn hộ
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable Integer id, @RequestBody ApartmentDTO apartmentDTO) {
        ApartmentDTO updatedApartment = apartmentService.updateApartment(id, apartmentDTO);
        return updatedApartment != null ? ResponseEntity.ok(updatedApartment) : ResponseEntity.notFound().build();
    }

    // Xóa căn hộ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Integer id) {
        return apartmentService.deleteApartment(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Lấy căn hộ theo area
    @GetMapping("/by-area")
    public ResponseEntity<List<ApartmentDTO>> getApartmentsByArea(@RequestParam Area area) {
        try {
            List<ApartmentDTO> apartments = apartmentService.getApartmentsByArea(area);
            return ResponseEntity.ok(apartments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    // Gửi email liên hệ căn hộ
    @PostMapping("/{id}/send-email")
    public ResponseEntity<String> sendEmail(@PathVariable Integer id, @RequestBody ApartmentContactDTO emailRequest) {
        try {
            ApartmentDTO apartment = apartmentService.getApartmentById(id);
            if (apartment == null) {
                return ResponseEntity.notFound().build();
            }

            long numberOfNights = ChronoUnit.DAYS.between(emailRequest.getCheckIn(), emailRequest.getCheckOut());

            String adminEmailContent = String.format("""
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                .container { max-width: 600px; margin: 0 auto; padding: 20px; background: #f9f9f9; border-radius: 10px; text-align: center; }
                h2 { color: #d9534f; text-align: center; }
                .section { margin-bottom: 15px; padding: 10px; background: #ffffff; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); text-align: left; }
                .section h3 { color: #007bff; margin-bottom: 8px; }
                .info { font-size: 14px; }
                .highlight { font-weight: bold; color: #000; }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Thông Báo: Có Yêu Cầu Đặt Căn Hộ Mới</h2>
                
                <div class="section">
                    <h3>Thông Tin Khách Hàng</h3>
                    <p class="info">
                        <span class="highlight">Họ và Tên:</span> %s<br>
                        <span class="highlight">Email:</span> %s<br>
                        <span class="highlight">Số Điện Thoại:</span> %s
                    </p>
                </div>

                <div class="section">
                    <h3>Chi Tiết Đặt Phòng</h3>
                    <p class="info">
                        <span class="highlight">Căn Hộ:</span> %s<br>
                        <span class="highlight">Check-in:</span> %s<br>
                        <span class="highlight">Check-out:</span> %s<br>
                        <span class="highlight">Số đêm:</span> %d<br>
                        <span class="highlight">Số người lớn:</span> %d<br>
                        <span class="highlight">Số trẻ em:</span> %d<br>
                    </p>
                </div>

                <div class="section">
                    <h3>Lời Nhắn</h3>
                    <p class="info">%s</p>
                </div>
            </div>
        </body>
        </html>
        """,
                    emailRequest.getFullName(),
                    emailRequest.getEmail(),
                    emailRequest.getPhoneNumber(),
                    apartment.getName(),
                    emailRequest.getCheckIn(),
                    emailRequest.getCheckOut(),
                    numberOfNights,
                    emailRequest.getAdults(),
                    emailRequest.getChildren(),
                    emailRequest.getMessage()
            );

            String customerEmailContent = String.format("""
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Anstay - Xác nhận yêu cầu đặt phòng</title>
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background: #f9f9f9; border-radius: 10px; text-align: center; }
        h2 { color: #28a745; }
        .content { font-size: 16px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Cảm ơn bạn đã để lại yêu cầu !</h2>
        <p class="content">
            Kính gửi %s,<br><br>
            Chúng tôi đã nhận được yêu cầu của bạn. Cảm ơn bạn đã tin tưởng và lựa chọn dịch vụ của chúng tôi.<br>
            Chúng tôi sẽ liên hệ sớm nhất để xác nhận thông tin và liên hệ lại với bạn.<br><br>
            Trân trọng,<br>
            Đội ngũ hỗ trợ
        </p>
    </div>
</body>
</html>
""",
                    emailRequest.getFullName()
            );

            // Send email to admin
            emailService.sendEmailWithTemplate(
                    "anstayresidence@gmail.com",
                    "Thông báo: Yêu cầu đặt căn hộ mới - " + apartment.getName(),
                    "Admin",
                    adminEmailContent
            );

            // Send confirmation email to customer
            emailService.sendEmailWithTemplate(
                    emailRequest.getEmail(),
                    "Xác nhận yêu cầu đặt căn hộ - " + apartment.getName(),
                    emailRequest.getFullName(),
                    customerEmailContent
            );

            return ResponseEntity.ok("Emails sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send emails: " + e.getMessage());
        }
    }

    private String formatPrice(BigDecimal price) {
        return String.format("%,d", price.longValue());
    }

    // ===== BỔ SUNG ENDPOINT API lấy căn hộ + phòng =====
    @GetMapping("/with-rooms")
    public ResponseEntity<List<ApartmentWithRoomsDTO>> getAllApartmentsWithRooms() {
        return ResponseEntity.ok(apartmentService.getAllApartmentsWithRooms());
    }
}
