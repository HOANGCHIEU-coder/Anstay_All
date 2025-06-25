    package com.anstay.entity;

    import com.anstay.enums.BookingStatus;
    import jakarta.persistence.*;
    import org.hibernate.annotations.CreationTimestamp;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "apartment_bookings")
    public class ApartmentBooking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "apartment_id", nullable = false)
        private Apartment apartment;

        @ManyToOne
        @JoinColumn(name = "room_id", nullable = false)
        private Room room;

        private LocalDate checkIn;
        private LocalDate checkOut;
        private BigDecimal totalPrice;

        @Enumerated(EnumType.STRING)
        private BookingStatus status = BookingStatus.PENDING;

        // Guest info
        private String guestName;
        private String guestPhone;
        private String guestEmail;
        private String guestIdentityNumber;
        private LocalDate guestBirthday;
        private String guestNationality;

        @Column(name = "created_at", updatable = false)
        @org.hibernate.annotations.CreationTimestamp
        private LocalDateTime createdAt;

    // Getters, setters, constructors... (bố có thể gen bằng IDE)

    public ApartmentBooking(Integer id, User user, Apartment apartment, Room room, LocalDate checkIn, LocalDate checkOut, BigDecimal totalPrice, BookingStatus status, String guestName, String guestPhone, String guestEmail, String guestIdentityNumber, LocalDate guestBirthday, String guestNationality, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.apartment = apartment;
        this.room = room;
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
        this.createdAt = createdAt;
    }

    public ApartmentBooking() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
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

    public LocalDate getGuestBirthday() {
        return guestBirthday;
    }

    public void setGuestBirthday(LocalDate guestBirthday) {
        this.guestBirthday = guestBirthday;
    }

    public String getGuestNationality() {
        return guestNationality;
    }

    public void setGuestNationality(String guestNationality) {
        this.guestNationality = guestNationality;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
