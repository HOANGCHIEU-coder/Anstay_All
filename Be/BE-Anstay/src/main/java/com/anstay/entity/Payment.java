package com.anstay.entity;

import com.anstay.enums.BookingType;
import com.anstay.enums.PaymentMethod;
import com.anstay.enums.PaymentStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // Loại booking: APARTMENT hoặc TOUR
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingType bookingType;

    // Id booking (apartment hoặc tour)
    @Column(nullable = false)
    private Integer bookingId;

    // User đặt, nullable để hỗ trợ khách vãng lai
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // Số tiền thanh toán
    @Column(nullable = false)
    private Double amount;

    // Phương thức thanh toán (ví dụ: MOMO, BANK_TRANSFER, ...)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // Mã giao dịch do hệ thống hoặc đối tác thanh toán sinh ra
    @Column(name = "transaction_id")
    private String transactionId;

    // Trạng thái thanh toán
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    // Thời gian tạo thanh toán
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt = new Date();

    // ========== Thông tin khách vãng lai (guest) ==========
    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_phone")
    private String guestPhone;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "guest_identity_number")
    private String guestIdentityNumber;

    @Column(name = "guest_birthday")
    private Date guestBirthday;

    @Column(name = "guest_nationality")
    private String guestNationality;

    // ==== Constructor mặc định ====
    public Payment() {}

    // ==== Getter & Setter ==== (có thể dùng Lombok @Data nếu muốn gọn hơn)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BookingType getBookingType() { return bookingType; }
    public void setBookingType(BookingType bookingType) { this.bookingType = bookingType; }

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getGuestPhone() { return guestPhone; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }

    public String getGuestEmail() { return guestEmail; }
    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }

    public String getGuestIdentityNumber() { return guestIdentityNumber; }
    public void setGuestIdentityNumber(String guestIdentityNumber) { this.guestIdentityNumber = guestIdentityNumber; }

    public Date getGuestBirthday() { return guestBirthday; }
    public void setGuestBirthday(Date guestBirthday) { this.guestBirthday = guestBirthday; }

    public String getGuestNationality() { return guestNationality; }
    public void setGuestNationality(String guestNationality) { this.guestNationality = guestNationality; }

    public Object getCheckOut() {
        return null;
    }

    public Object getCheckIn() {
        return null;
    }

    public Object getRoomType() {
        return null;
    }

    public Object getCheckInDate() {
        return null;
    }

    public Object getCheckOutDate() {
        return null;
    }

    public Object getReason() {
        return null;
    }
}
