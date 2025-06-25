package com.anstay.controller;

import com.anstay.entity.Contact;

import com.anstay.service.ContactService;
import com.anstay.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        try {
            Contact createdContact = contactService.createContact(contact);

            // Gửi email thông báo đến admin
            String emailContent = String.format("""
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                .container { max-width: 600px; margin: 0 auto; padding: 20px; background: #f9f9f9; border-radius: 10px; text-align: center; }
                h2 { color: #d9534f; text-align: center; }
                .logo { max-width: 150px; margin-bottom: 20px; }
                .section { margin-bottom: 15px; padding: 10px; background: #ffffff; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); text-align: left; }
                .section h3 { color: #007bff; margin-bottom: 8px; }
                .info { font-size: 14px; }
                .highlight { font-weight: bold; color: #000; }
                .footer { margin-top: 20px; text-align: center; font-size: 12px; color: #777; }
            </style>
        </head>
        <body>
            <div class="container">
                <img src="https://i.ibb.co/35SyTcnX/Anstay.png" alt="Anstay Logo" class="logo">
                <h2>Thông Báo Liên Hệ Mới</h2>
                
                <div class="section">
                    <h3>Thông Tin Người Liên Hệ</h3>
                    <p class="info">
                        <span class="highlight">Họ và Tên:</span> %s <br>
                        <span class="highlight">Email:</span> %s <br>
                        <span class="highlight">Số Điện Thoại:</span> %s
                    </p>
                </div>

                <div class="section">
                    <h3>Nội Dung Liên Hệ</h3>
                    <p class="info">%s</p>
                </div>

                <div class="section">
                    <h3>Thời Gian Gửi</h3>
                    <p class="info">%s</p>
                </div>

                <div class="footer">
                    <p>Trân trọng,<br><strong>Hệ thống Anstay</strong></p>
                </div>
            </div>
        </body>
        </html>
        """,
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getMessage(),
                    contact.getCreatedAt()
            );

            emailService.sendEmailWithTemplate(
                    "anstayresidence@gmail.com",  // Email admin từ application.properties
                    "Thông báo: Có liên hệ mới từ " + contact.getName(),
                    "Admin",
                    emailContent
            );

            return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
        } catch (MessagingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
