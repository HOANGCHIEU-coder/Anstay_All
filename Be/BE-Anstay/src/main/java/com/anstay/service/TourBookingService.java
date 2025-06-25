package com.anstay.service;

import com.anstay.dto.TourBookingDTO;
import com.anstay.dto.UserDTO;
import com.anstay.enums.BookingStatus;
import com.anstay.entity.TourBooking;
import com.anstay.repository.TourBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourBookingService {
    private final TourBookingRepository tourBookingRepository;

    public TourBookingService(TourBookingRepository tourBookingRepository) {
        this.tourBookingRepository = tourBookingRepository;
    }

    @Transactional
    public TourBookingDTO createBooking(TourBookingDTO request) {
        TourBooking booking = new TourBooking();
        booking.setUserId(request.getUserId());
        booking.setTourId(request.getTourId());
        booking.setCheckIn(request.getCheckIn());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setStatus(BookingStatus.PENDING);

        booking = tourBookingRepository.save(booking);
        return convertToDTO(booking);
    }

    public List<TourBookingDTO> getAllBookings() {
        return tourBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TourBookingDTO> getBookingById(Long id) {
        return tourBookingRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public Optional<TourBookingDTO> updateBookingStatus(Long id, BookingStatus status) {
        Optional<TourBooking> bookingOpt = tourBookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            TourBooking booking = bookingOpt.get();
            booking.setStatus(status);
            tourBookingRepository.save(booking);
            return Optional.of(convertToDTO(booking));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteBooking(Integer id) {
        if (tourBookingRepository.existsById(id)) {
            tourBookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TourBookingDTO convertToDTO(TourBooking booking) {

        return new TourBookingDTO(
                booking.getId(),
                booking.getUserId(),
                booking.getTourId(),
                booking.getCheckIn(),
                booking.getTotalPrice(),
                booking.getStatus()

        );
    }
}
