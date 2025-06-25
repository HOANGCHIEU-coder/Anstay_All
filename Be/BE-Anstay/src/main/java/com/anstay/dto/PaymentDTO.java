package com.anstay.dto;

import com.anstay.enums.BookingType;
import com.anstay.enums.PaymentMethod;
import com.anstay.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.Date;

public class PaymentDTO {
    private Integer id;
    private BookingType bookingType;
    private Integer bookingId;
    private Integer userId;
    private String userFullName;
    private Double amount;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private PaymentStatus status;
    private Date createdAt;

    // ====== Các trường khách vãng lai (guest) ======
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private String guestIdentityNumber;
    private Date guestBirthday;
    private String guestNationality;
    private LocalDate checkIn;
    private LocalDate checkOut;






    // ======= Constructor có đủ trường (bạn tự bổ sung thêm nếu cần) =======
    public PaymentDTO() {}

    public PaymentDTO(
            Integer id,
            BookingType bookingType,
            Integer bookingId,
            Integer userId,
            String userFullName,
            Double amount,
            PaymentMethod paymentMethod,
            String transactionId,
            PaymentStatus status,
            Date createdAt,
            String guestName,
            String guestPhone,
            String guestEmail,
            String guestIdentityNumber,
            Date guestBirthday,
            String guestNationality
    ) {
        this.id = id;
        this.bookingType = bookingType;
        this.bookingId = bookingId;
        this.userId = userId;
        this.userFullName = userFullName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
        this.createdAt = createdAt;
        this.guestName = guestName;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.guestIdentityNumber = guestIdentityNumber;
        this.guestBirthday = guestBirthday;
        this.guestNationality = guestNationality;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestIdentityNumber() {
        return guestIdentityNumber;
    }

    public void setGuestIdentityNumber(String guestIdentityNumber) {
        this.guestIdentityNumber = guestIdentityNumber;
    }

    public Date getGuestBirthday() {
        return guestBirthday;
    }

    public void setGuestBirthday(Date guestBirthday) {
        this.guestBirthday = guestBirthday;
    }

    public String getGuestNationality() {
        return guestNationality;
    }

    public void setGuestNationality(String guestNationality) {
        this.guestNationality = guestNationality;
    }
}
