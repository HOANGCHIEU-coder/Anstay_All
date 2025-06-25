package com.anstay.service;

import com.anstay.dto.TourDTO;
import com.anstay.entity.Tour;
import com.anstay.repository.TourCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourCrudService {

    @Autowired
    private TourCrudRepository tourCrudRepository;

    // 🟢 Lấy danh sách tất cả Tour
    public List<TourDTO> getAllTours() {
        List<Tour> tours = tourCrudRepository.findAll();
        return tours.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 🟢 Lấy thông tin một Tour
    public TourDTO getTourById(Integer id) {
        Optional<Tour> optionalTour = tourCrudRepository.findById(id);
        return optionalTour.map(this::convertToDTO).orElse(null);
    }

    // 🟢 Tạo mới một Tour
    public TourDTO createTour(TourDTO tourDTO) {
        Tour tour = new Tour();
        tour.setName(tourDTO.getName());
        tour.setDescription(tourDTO.getDescription());
        tour.setPrice(tourDTO.getPrice());
        tour.setDurationDays(tourDTO.getDurationDays());
        tour.setDiscountPercent(tourDTO.getDiscountPercent());
        tour.setCreatedAt(tourDTO.getCreatedAt());
        tour.setArea(tourDTO.getArea());
        tour.setTransportation(tourDTO.getTransportation());
        tour.setHotel(tourDTO.getHotel());
        tour = tourCrudRepository.save(tour);
        return convertToDTO(tour);
    }

    // 🟢 Cập nhật thông tin Tour
    public TourDTO updateTour(Integer id, TourDTO tourDTO) {
        Optional<Tour> optionalTour = tourCrudRepository.findById(id);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            tour.setName(tourDTO.getName());
            tour.setDescription(tourDTO.getDescription());
            tour.setPrice(tourDTO.getPrice());
            tour.setDurationDays(tourDTO.getDurationDays());
            tour.setDiscountPercent(tourDTO.getDiscountPercent());
            tour.setArea(tourDTO.getArea());
            tour.setTransportation(tourDTO.getTransportation());
            tour.setHotel(tourDTO.getHotel());
            tour = tourCrudRepository.save(tour);
            return convertToDTO(tour);
        }
        return null;
    }

    // 🟢 Xóa một Tour
    public void deleteTour(Integer id) {
        tourCrudRepository.deleteById(id);
    }

    // 🟢 Hàm chuyển đổi Entity sang DTO
    private TourDTO convertToDTO(Tour tour) {
        return new TourDTO(
                tour.getId(),
                tour.getName(),
                tour.getDescription(),
                tour.getPrice(),
                tour.getDurationDays(),
                tour.getDiscountPercent(),
                tour.getCreatedAt(),
                null, // Không cần lịch trình
                null,
                tour.getArea(),
                tour.getTransportation(),
                tour.getHotel()
        );
    }
}
