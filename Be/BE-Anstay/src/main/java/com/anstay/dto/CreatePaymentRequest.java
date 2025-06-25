package com.anstay.dto;

import com.anstay.enums.BookingType;
import com.anstay.enums.PaymentMethod;

import java.util.Date;

public class CreatePaymentRequest {
    private BookingType bookingType;
    private Integer bookingId;
    private Integer userId;
    private Double amount;
    private PaymentMethod paymentMethod;

    // Thông tin khách vãng lai
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private String guestIdentityNumber;
    private Date guestBirthday;
    private String guestNationality;

    // ==== Getter & Setter ====
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
