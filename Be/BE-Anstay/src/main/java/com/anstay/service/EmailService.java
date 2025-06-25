package com.anstay.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }

    public void sendEmailWithTemplate(String to, String subject, String name, String content) throws MessagingException {
        String htmlTemplate = """
                <div style="font-family: Arial, sans-serif; padding: 20px;">
                    <h2>Xin chào %s,</h2>
                    <div style="margin: 20px 0;">
                        %s
                    </div>
                    <p>Trân trọng,<br/>Anstay Team</p>
                </div>
                """.formatted(name, content);

        sendEmail(to, subject, htmlTemplate);
    }

    //Gửi email thông báo đơn hàng mới
    public void sendOrderNotification(String orderId, String customerName, String customerEmail,
                                      String phone, String roomType, String checkIn, String checkOut,
                                      Double amount, String status) {
        try {
            String subject = "🏨 Đơn hàng mới #" + orderId + " - " + getStatusText(status);
            String adminEmail = "anstayresidence@gmail.com";

            String emailContent = createOrderEmailTemplate(orderId, customerName, customerEmail,
                    phone, roomType, checkIn, checkOut,
                    amount, status);

            sendEmail(adminEmail, subject, emailContent);
            System.out.println("✅ Đã gửi email thông báo đơn hàng #" + orderId);

        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi email thông báo: " + e.getMessage());
        }
    }

    /**
     * Tạo template email đơn hàng
     */
    private String createOrderEmailTemplate(String orderId, String customerName, String customerEmail,
                                            String phone, String roomType, String checkIn, String checkOut,
                                            Double amount, String status) {
        String statusColor = getStatusColor(status);
        String statusText = getStatusText(status);
        String currentTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        return """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                <div style="background: #2c3e50; color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0;">
                    <h2>🏨 THÔNG BÁO ĐƠN HÀNG MỚI</h2>
                </div>
                
                <div style="background: #f8f9fa; padding: 20px; border: 1px solid #dee2e6; border-radius: 0 0 8px 8px;">
                    <div style="background: #fff3cd; padding: 15px; border-radius: 6px; border-left: 4px solid #ffc107; margin: 15px 0;">
                        <strong>Có đơn hàng mới cần xử lý!</strong>
                    </div>
                    
                    <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                        <strong>📋 ID Đơn hàng:</strong> #%s
                        <span style="display: inline-block; padding: 5px 15px; border-radius: 20px; color: white; font-weight: bold; background: %s; margin-left: 10px;">%s</span>
                    </div>
                    
                    <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                        <strong>👤 Khách hàng:</strong> %s
                    </div>
                    
                    <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                        <strong>📧 Email:</strong> %s
                    </div>
                    
                    <div style="margin: 12px 0; padding: 12px; background: white; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                        <strong>📱 Số điện thoại:</strong> %s
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
                        <strong>🕐 Thời gian tạo:</strong> %s
                    </div>
                </div>
                
                <div style="text-align: center; margin-top: 20px; color: #6c757d; font-size: 12px;">
                    <p>📧 Email tự động từ hệ thống quản lý khách sạn Anstay</p>
                    <p>⚠️ Vui lòng không reply email này</p>
                </div>
            </div>
            """.formatted(
                orderId, statusColor, statusText,
                safeString(customerName),
                safeString(customerEmail),
                safeString(phone),
                safeString(roomType),
                safeString(checkIn),
                safeString(checkOut),
                formatMoney(amount),
                currentTime
        );
    }

    /**
     * Helper methods
     */
    private String getStatusColor(String status) {
        return switch (status != null ? status.toUpperCase() : "UNKNOWN") {
            case "PENDING" -> "#ff9800";
            case "CONFIRMED" -> "#4caf50";
            case "COMPLETED" -> "#2196f3";
            case "CANCELLED" -> "#f44336";
            default -> "#6c757d";
        };
    }

    private String getStatusText(String status) {
        return switch (status != null ? status.toUpperCase() : "UNKNOWN") {
            case "PENDING" -> "Chờ xác nhận";
            case "CONFIRMED" -> "Đã xác nhận";
            case "COMPLETED" -> "Hoàn thành";
            case "CANCELLED" -> "Đã hủy";
            default -> "Không xác định";
        };
    }

    private String safeString(String value) {
        return value != null ? value : "N/A";
    }

    private String formatMoney(Double amount) {
        if (amount == null) return "0";
        return String.format("%,.0f", amount);
    }
}