package com.anstay.controller;

import com.anstay.dto.TourContactDTO;
import com.anstay.dto.TourDTO;
import com.anstay.entity.Contact;
import com.anstay.entity.Tour;
import com.anstay.enums.Area;
import com.anstay.service.ContactService;
import com.anstay.service.EmailService;
import com.anstay.service.TourService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    // 🟢 API lấy danh sách tất cả Tour
    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        List<TourDTO> tours = tourService.getAllTours();
        return ResponseEntity.ok(tours);
    }

    // 🟢 API lấy thông tin 1 Tour theo ID
    @GetMapping("/{id}")
    public ResponseEntity<List<TourDTO>> getTourById(@PathVariable Integer id) {
        System.out.println("🔍 API được gọi với id: " + id);
        try {
            TourDTO tour = tourService.getTourById(id);
            if (tour == null) {
                System.out.println("❌ Không tìm thấy tour với id: " + id);
                return ResponseEntity.ok(Collections.emptyList());
            }
            List<TourDTO> tours = Collections.singletonList(tour);
            System.out.println("✅ Tour tìm thấy: " + tours);
            return ResponseEntity.ok(tours);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tìm tour: " + e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/by-area")
    public ResponseEntity<List<TourDTO>> getToursByArea(@RequestParam Area area) {
        try {
            List<TourDTO> tours = tourService.getAllToursByArea(area);
            return ResponseEntity.ok(tours);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/{tourId}/contact")
    public ResponseEntity<?> submitTourContact(@PathVariable Integer tourId, @RequestBody TourContactDTO contactDTO) {
        try {
            // Get tour information
            TourDTO tour = tourService.getTourById(tourId);
            if (tour == null) {
                return ResponseEntity.notFound().build();
            }

            // Create contact
            Contact contact = new Contact();
            contact.setName(contactDTO.getName());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhone(contactDTO.getPhone());
            contact.setMessage(contactDTO.getMessage());

            Contact savedContact = contactService.createContact(contact);

            // Send email notification
            emailService.sendEmailWithTemplate(
                    "anstayresidence@gmail.com",
                    "Thông báo: Có liên hệ mới về tour " + tour.getName(),
                    "Admin",
                    String.format("""
                Thông tin liên hệ về tour:
                <br/><br/>
                <b>Tour:</b> %s
                <br/>
                <b>Tên khách hàng:</b> %s
                <br/>
                <b>Email:</b> %s
                <br/>
                <b>Số điện thoại:</b> %s
                <br/>
                <b>Nội dung:</b> %s
                <br/><br/>
                <b>Thời gian:</b> %s
                """,
                            tour.getName(),
                            contact.getName(),
                            contact.getEmail(),
                            contact.getPhone(),
                            contact.getMessage(),
                            contact.getCreatedAt()
                    )
            );

            return ResponseEntity.ok(savedContact);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
