package com.anstay.dto;

import com.anstay.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ApartmentBookingDTO {
    private Integer id;
    private Integer userId;
    private Integer apartmentId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal totalPrice;
    private BookingStatus status;
    // Guest info
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private String guestIdentityNumber;
    private LocalDate guestBirthday;
    private String guestNationality;

    // ===== Thêm mới =====
    private String area;           // VD: "HA_NOI", "HA_LONG"
    private String apartmentName;  // VD: "A La Carte Hạ Long"

    // ====== Constructor đầy đủ ======
    public ApartmentBookingDTO(Integer id, Integer userId, Integer apartmentId, Long roomId, LocalDate checkIn, LocalDate checkOut,
                               BigDecimal totalPrice, BookingStatus status, String guestName, String guestPhone,
                               String guestEmail, String guestIdentityNumber, LocalDate guestBirthday, String guestNationality) {
        this.id = id;
        this.userId = userId;
        this.apartmentId = apartmentId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.status = status;
        this.guestName = guestName;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.guestIdentityNumber = guestIdentityNumber;
        this.guestBirthday = guestBirthday;
        this.guestNationality = guestNationality;
    }

    // ====== Getter & Setter ======
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getApartmentId() { return apartmentId; }
    public void setApartmentId(Integer apartmentId) { this.apartmentId = apartmentId; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getGuestPhone() { return guestPhone; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }
    public String getGuestEmail() { return guestEmail; }
    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }
    public String getGuestIdentityNumber() { return guestIdentityNumber; }
    public void setGuestIdentityNumber(String guestIdentityNumber) { this.guestIdentityNumber = guestIdentityNumber; }
    public LocalDate getGuestBirthday() { return guestBirthday; }
    public void setGuestBirthday(LocalDate guestBirthday) { this.guestBirthday = guestBirthday; }
    public String getGuestNationality() { return guestNationality; }
    public void setGuestNationality(String guestNationality) { this.guestNationality = guestNationality; }

    // ===== Getter & Setter cho 2 trường mới =====
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getApartmentName() { return apartmentName; }
    public void setApartmentName(String apartmentName) { this.apartmentName = apartmentName; }
}
