package com.anstay.service;

import com.anstay.dto.ApartmentBookingDTO;
import com.anstay.entity.*;
import com.anstay.enums.BookingStatus;
import com.anstay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ApartmentBookingService {

    @Autowired private ApartmentBookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ApartmentRepository apartmentRepository;
    @Autowired private RoomRepository roomRepository;

    // Lấy tất cả booking
    public List<ApartmentBookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy booking theo ID
    public ApartmentBookingDTO getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Kiểm tra phòng còn trống trong khoảng ngày
    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<BookingStatus> statuses = List.of(BookingStatus.HOLD, BookingStatus.CONFIRMED);
        List<ApartmentBooking> overlap = bookingRepository.findActiveBookingsByRoomAndDateRange(
                roomId, checkIn, checkOut, statuses
        );
        return overlap.isEmpty();
    }

    // Tạo booking mới (giữ chỗ, HOLD)
    public ApartmentBookingDTO createBooking(ApartmentBookingDTO bookingDTO) {
        if (bookingDTO.getRoomId() == null || bookingDTO.getApartmentId() == null
                || bookingDTO.getCheckIn() == null || bookingDTO.getCheckOut() == null) {
            throw new IllegalArgumentException("Missing room, apartment, check-in or check-out info.");
        }
        Apartment apartment = apartmentRepository.findById(bookingDTO.getApartmentId()).orElse(null);
        Room room = roomRepository.findById(bookingDTO.getRoomId()).orElse(null);
        User user = bookingDTO.getUserId() != null
                ? userRepository.findById(bookingDTO.getUserId()).orElse(null)
                : null;

        if (apartment == null || room == null) return null;
        if (!isRoomAvailable(room.getId(), bookingDTO.getCheckIn(), bookingDTO.getCheckOut())) {
            throw new IllegalStateException("Room already booked or held for this date range!");
        }

        ApartmentBooking booking = new ApartmentBooking();
        booking.setUser(user);
        booking.setApartment(apartment);
        booking.setRoom(room);
        booking.setCheckIn(bookingDTO.getCheckIn());
        booking.setCheckOut(bookingDTO.getCheckOut());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setStatus(BookingStatus.HOLD); // Luôn giữ HOLD khi mới tạo

        // Guest info
        booking.setGuestName(bookingDTO.getGuestName());
        booking.setGuestPhone(bookingDTO.getGuestPhone());
        booking.setGuestEmail(bookingDTO.getGuestEmail());
        booking.setGuestIdentityNumber(bookingDTO.getGuestIdentityNumber());
        booking.setGuestBirthday(bookingDTO.getGuestBirthday());
        booking.setGuestNationality(bookingDTO.getGuestNationality());

        ApartmentBooking saved = bookingRepository.save(booking);
        return convertToDTO(saved);
    }

    public List<ApartmentBookingDTO> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ApartmentBookingDTO> getBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ApartmentBookingDTO> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(BookingStatus.valueOf(status)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Cập nhật thông tin booking (nâng cấp lên CONFIRMED, hoặc update info)
    public ApartmentBookingDTO updateBooking(Integer id, ApartmentBookingDTO dto) {
        Optional<ApartmentBooking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isEmpty()) return null;
        ApartmentBooking booking = bookingOpt.get();

        // Update các trường (trừ createdAt, id)
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            booking.setUser(user);
        }
        if (dto.getApartmentId() != null) {
            Apartment apartment = apartmentRepository.findById(dto.getApartmentId()).orElse(null);
            booking.setApartment(apartment);
        }
        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId()).orElse(null);
            booking.setRoom(room);
        }
        if (dto.getCheckIn() != null) booking.setCheckIn(dto.getCheckIn());
        if (dto.getCheckOut() != null) booking.setCheckOut(dto.getCheckOut());
        if (dto.getTotalPrice() != null) booking.setTotalPrice(dto.getTotalPrice());
        if (dto.getStatus() != null) booking.setStatus(dto.getStatus());

        booking.setGuestName(dto.getGuestName());
        booking.setGuestPhone(dto.getGuestPhone());
        booking.setGuestEmail(dto.getGuestEmail());
        booking.setGuestIdentityNumber(dto.getGuestIdentityNumber());
        booking.setGuestBirthday(dto.getGuestBirthday());
        booking.setGuestNationality(dto.getGuestNationality());

        return convertToDTO(bookingRepository.save(booking));
    }

    // Xóa booking
    public boolean deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Chuyển entity sang DTO (có thêm area, apartmentName)
    private ApartmentBookingDTO convertToDTO(ApartmentBooking booking) {
        ApartmentBookingDTO dto = new ApartmentBookingDTO(
                booking.getId(),
                booking.getUser() != null ? booking.getUser().getId() : null,
                booking.getApartment().getId(),
                booking.getRoom().getId(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getGuestName(),
                booking.getGuestPhone(),
                booking.getGuestEmail(),
                booking.getGuestIdentityNumber(),
                booking.getGuestBirthday(),
                booking.getGuestNationality()
        );
        // Nếu DTO có field area & apartmentName
        dto.setArea(booking.getApartment().getArea().name());
        dto.setApartmentName(booking.getApartment().getName());
        return dto;
    }

    // ===== BỔ SUNG: Lấy tất cả booking, group theo khu vực (area) =====
// Chỉ lấy booking status = HOLD, group theo area
    public Map<String, List<ApartmentBookingDTO>> getAllHoldBookingsGroupedByArea() {
        List<ApartmentBooking> bookings = bookingRepository.findAllByStatusWithApartment(BookingStatus.HOLD);
        List<ApartmentBookingDTO> dtos = bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        // Group by area field
        return dtos.stream().collect(Collectors.groupingBy(ApartmentBookingDTO::getArea));
    }

}
