package com.anstay.controller;

import com.anstay.dto.CreatePaymentRequest;
import com.anstay.dto.PaymentDTO;
import com.anstay.entity.Payment;
import com.anstay.repository.PaymentRepository;
import com.anstay.service.EmailService;
import com.anstay.service.PaymentService;
import com.anstay.enums.PaymentStatus;
import com.anstay.util.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = {"https://anstay.com.vn",  "http://localhost:5174" ,"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")

public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private EmailService emailService;


    // 1. Tạo thanh toán qua Momo (chuẩn cho FE gọi khi đặt phòng)
    @PostMapping("/momo")
    public ResponseEntity<?> createMomoPayment(@RequestBody CreatePaymentRequest request) {
        String payUrl = paymentService.createMomoPayment(request);
        return ResponseEntity.ok(Collections.singletonMap("payUrl", payUrl));
    }

    // 2. Nhận IPN từ Momo (notify sau khi khách thanh toán xong)
    @PostMapping("/momo/ipn")
    public ResponseEntity<?> momoIpnNotify(@RequestBody Map<String, Object> notifyData) {
        paymentService.handleMomoIpn(notifyData);
        return ResponseEntity.ok("OK");
    }

    // 3. Lấy chi tiết 1 payment
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Integer id) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);
        return ResponseEntity.ok(paymentDTO);
    }

    // 4. Lấy tất cả payment (cho admin)
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // 5. Lấy lịch sử thanh toán của 1 user (tùy nhu cầu)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByUser(@PathVariable Integer userId) {
        List<PaymentDTO> list = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(list);
    }

    // 6. Lấy theo trạng thái (completed, failed, ...)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentDTO> list = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentDTO> getByTransactionId(@PathVariable String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy giao dịch!"));
        return ResponseEntity.ok(PaymentMapper.toDTO(payment));
    }

    @PostMapping("/cash")
    public ResponseEntity<PaymentDTO> createCashPayment(@RequestBody CreatePaymentRequest request) {
        PaymentDTO paymentDTO = paymentService.createCashPayment(request);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/pending")
    public List<PaymentDTO> getPendingPayments() {
        return paymentService.getPendingPayments();
    }

    @GetMapping("/report/with-checkin-checkout")
    public ResponseEntity<?> getPaymentsWithCheckInOut() {
        return ResponseEntity.ok(paymentService.getPaymentsWithCheckInOut());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer id, @RequestBody PaymentDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));

        // Cập nhật thông tin từ DTO
        payment.setGuestName(dto.getGuestName());
        payment.setGuestPhone(dto.getGuestPhone());
        payment.setGuestEmail(dto.getGuestEmail());
        payment.setGuestIdentityNumber(dto.getGuestIdentityNumber());
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());
        payment.setBookingType(dto.getBookingType());

        paymentRepository.save(payment);
        // Gửi email thông báo đơn hàng mới
        try {
            String subject = "🏨 Đơn hàng mới #" + payment.getId() + " - " + payment.getStatus();
            String adminEmail = "anstayresidence@anstay.com.vn";

            String emailContent = """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
            <div style="background: #2c3e50; color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0;">
                <h2>🏨 THÔNG BÁO ĐƠN HÀNG MỚI</h2>
            </div>
            
            <div style="background: #f8f9fa; padding: 20px; border: 1px solid #dee2e6; border-radius: 0 0 8px 8px;">
                <div style="background: #fff3cd; padding: 15px; border-radius: 6px; border-left: 4px solid #ffc107; margin: 15px 0;">
                    <strong>⚠️ Có đơn hàng mới cần xử lý!</strong>
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📋 ID Đơn hàng:</strong> #%s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>👤 Tên khách hàng:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📧 Email:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📱 Số điện thoại:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>🆔 CCCD:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>🏠 Loại phòng:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📅 Check-in:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📅 Check-out:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>💰 Tổng tiền:</strong> %s VNĐ
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>📊 Trạng thái:</strong> %s
                </div>
                
                <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <strong>🕐 Thời gian tạo:</strong> %s
                </div>
            </div>
            
            <div style="text-align: center; margin-top: 20px; color: #6c757d; font-size: 12px;">
                <p>📧 Email tự động từ hệ thống Anstay Hotel</p>
                <p>⚠️ Vui lòng không reply email này</p>
            </div>
        </div>
        """.formatted(
                    payment.getId(),
                    payment.getGuestName() != null ? payment.getGuestName() : "N/A",
                    payment.getGuestEmail() != null ? payment.getGuestEmail() : "N/A",
                    payment.getGuestPhone() != null ? payment.getGuestPhone() : "N/A",
                    payment.getGuestIdentityNumber() != null ? payment.getGuestIdentityNumber() : "N/A",
                    payment.getBookingType() != null ? payment.getBookingType() : "N/A",
                    dto.getCheckIn() != null ? dto.getCheckIn().toString() : "Chưa có thông tin",
                    dto.getCheckOut() != null ? dto.getCheckOut().toString() : "Chưa có thông tin",
                    payment.getAmount() != null ? String.format("%,.0f", payment.getAmount()) : "0",
                    payment.getStatus() != null ? payment.getStatus() : "PENDING",
                    java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            );

            emailService.sendEmail(adminEmail, subject, emailContent);
            System.out.println("✅ Đã gửi email thông báo đơn hàng #" + payment.getId());

        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi email: " + e.getMessage());
        }

        return ResponseEntity.ok("Cập nhật thành công");
    }
    //Đơn hàng thành công
    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedPayments() {
        return ResponseEntity.ok(paymentService.getCompletedPayments());
    }

    @GetMapping("/history")
    public ResponseEntity<?> getPaymentHistory(
            @RequestParam(required = false) String status
    ) {
        try {
            List<Payment> payments;

            if (status != null && !status.isEmpty()) {
                try {
                    // Convert String thành PaymentStatus enum
                    PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
                    payments = paymentRepository.findByStatus(paymentStatus);
                    System.out.println("✅ Lấy đơn hàng với status: " + paymentStatus + " - Tìm thấy: " + payments.size());
                } catch (IllegalArgumentException e) {
                    // Nếu status không hợp lệ, trả về empty list
                    System.err.println("❌ Invalid status: " + status + ". Valid values: PENDING, COMPLETED, FAILED, REFUNDED");
                    payments = new ArrayList<>();
                }
            } else {
                // CHỈ LẤY 2 trạng thái: FAILED, REFUNDED (KHÔNG LẤY COMPLETED)
                List<PaymentStatus> statuses = Arrays.asList(
                        PaymentStatus.FAILED,
                        PaymentStatus.REFUNDED
                );
                payments = paymentRepository.findByStatusIn(statuses);
                System.out.println("✅ Lấy lịch sử vấn đề (FAILED + REFUNDED) - Tìm thấy: " + payments.size());
            }

            // Sắp xếp theo ngày tạo mới nhất (nếu có createdAt)
            payments.sort((a, b) -> {
                if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
                if (a.getCreatedAt() == null) return 1;
                if (b.getCreatedAt() == null) return -1;
                return b.getCreatedAt().compareTo(a.getCreatedAt());
            });

            // Convert sang định dạng frontend cần
            List<Map<String, Object>> result = payments.stream().map(payment -> {
                Map<String, Object> item = new HashMap<>();
                item.put("id", payment.getId());
                item.put("roomType", payment.getRoomType() != null ? payment.getRoomType() : "APARTMENT");
                item.put("customerName", payment.getGuestName() != null ? payment.getGuestName() : "N/A");
                item.put("email", payment.getGuestEmail() != null ? payment.getGuestEmail() : "N/A");
                item.put("phone", payment.getGuestPhone() != null ? payment.getGuestPhone() : "N/A");
                item.put("amount", payment.getAmount() != null ? payment.getAmount() : 0.0);
                item.put("checkInDate", payment.getCheckInDate() != null ? payment.getCheckInDate() : "N/A");
                item.put("checkOutDate", payment.getCheckOutDate() != null ? payment.getCheckOutDate() : "N/A");
                item.put("status", payment.getStatus() != null ? payment.getStatus().toString() : "COMPLETED");
                item.put("createdAt", payment.getCreatedAt());
                item.put("reason", payment.getReason()); // Có thể null
                return item;
            }).collect(Collectors.toList());

            // Thống kê chỉ cho 2 trạng thái
            long failed = result.stream().filter(p -> "FAILED".equals(p.get("status"))).count();
            long refunded = result.stream().filter(p -> "REFUNDED".equals(p.get("status"))).count();

            Map<String, Object> stats = new HashMap<>();
            stats.put("total", result.size());
            stats.put("failed", failed);
            stats.put("refunded", refunded);
            // Không có completed nữa

            // Response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            response.put("stats", stats);

            System.out.println("📊 Thống kê đơn hàng có vấn đề: Total=" + result.size() + ", Failed=" + failed + ", Refunded=" + refunded);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Lỗi API history: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Lỗi server: " + e.getMessage());
            errorResponse.put("data", new ArrayList<>());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}




