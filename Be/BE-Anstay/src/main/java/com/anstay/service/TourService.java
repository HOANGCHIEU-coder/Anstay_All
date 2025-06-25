package com.anstay.service;

import com.anstay.dto.TourDTO;
import com.anstay.dto.TourImageDTO;
import com.anstay.dto.TourScheduleDTO;
import com.anstay.dto.TourScheduleDetailDTO;
import com.anstay.entity.Tour;
import com.anstay.enums.Area;
import com.anstay.repository.TourRepository;
import com.anstay.repository.TourScheduleRepository;
import com.anstay.repository.TourImageRepository;
import com.anstay.repository.TourScheduleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Autowired
    private TourImageRepository tourImageRepository;

    @Autowired
    private TourScheduleDetailRepository tourScheduleDetailRepository;

    // 🟢 Lấy danh sách tất cả Tour kèm lịch trình và hình ảnh
    public List<TourDTO> getAllTours() {
        List<Tour> tours = tourRepository.findAll();

        return tours.stream().map(tour -> {
            // 🟢 Lấy danh sách lịch trình
            List<TourScheduleDTO> schedules = tourScheduleRepository.findByTourId(tour.getId()).stream()
                    .map(schedule -> new TourScheduleDTO(
                            schedule.getId(),
                            schedule.getTour().getId(),
                            schedule.getDayNumber(),
                            schedule.getTitle(),
                            schedule.getDescription(),
                            getScheduleDetails(schedule.getId()) // 🟢 Gán danh sách chi tiết lịch trình
                    )).collect(Collectors.toList());

            // 🟢 Lấy danh sách hình ảnh
            List<TourImageDTO> images = tourImageRepository.findByTourId(tour.getId()).stream()
                    .map(image -> new TourImageDTO(
                            image.getId(),
                            image.getTour().getId(),
                            image.getImageUrl(),
                            image.isFeatured()
                    )).collect(Collectors.toList());

            return new TourDTO(
                    tour.getId(),
                    tour.getName(),
                    tour.getDescription(),
                    tour.getPrice(),
                    tour.getDurationDays(),
                    tour.getDiscountPercent(),
                    tour.getCreatedAt(),
                    schedules, // 🟢 Gán lịch trình vào DTO
                    images,
                    tour.getArea(),
                    tour.getTransportation(),
                    tour.getHotel()
            );
        }).collect(Collectors.toList());
    }

    // 🟢 Hàm lấy danh sách chi tiết lịch trình theo schedule_id
    private List<TourScheduleDetailDTO> getScheduleDetails(Integer scheduleId) {
        return tourScheduleDetailRepository.findByScheduleId(scheduleId).stream()
                .map(detail -> new TourScheduleDetailDTO(
                        detail.getId(),
                        detail.getTimeSlot(),
                        detail.getDescription()
                )).collect(Collectors.toList());
    }

    public TourDTO getTourById(Integer id) {
        return tourRepository.findById(id).map(tour -> {
            // Get schedules with details
            List<TourScheduleDTO> schedules = tourScheduleRepository.findByTourId(tour.getId()).stream()
                    .map(schedule -> new TourScheduleDTO(
                            schedule.getId(),
                            schedule.getTour().getId(),
                            schedule.getDayNumber(),
                            schedule.getTitle(),
                            schedule.getDescription(),
                            getScheduleDetails(schedule.getId())
                    )).collect(Collectors.toList());

            // Get images
            List<TourImageDTO> images = tourImageRepository.findByTourId(tour.getId()).stream()
                    .map(image -> new TourImageDTO(
                            image.getId(),
                            image.getTour().getId(),
                            image.getImageUrl(),
                            image.isFeatured()
                    )).collect(Collectors.toList());

            return new TourDTO(
                    tour.getId(),
                    tour.getName(),
                    tour.getDescription(),
                    tour.getPrice(),
                    tour.getDurationDays(),
                    tour.getDiscountPercent(),
                    tour.getCreatedAt(),
                    schedules,
                    images,
                    tour.getArea(),
                    tour.getTransportation(),
                    tour.getHotel()
            );
        }).orElse(null);
    }
    public List<TourDTO> getAllToursByArea(Area area) {
        List<Tour> tours = tourRepository.findByArea(area);

        return tours.stream().map(tour -> {
            List<TourScheduleDTO> schedules = tourScheduleRepository.findByTourId(tour.getId()).stream()
                    .map(schedule -> new TourScheduleDTO(
                            schedule.getId(),
                            schedule.getTour().getId(),
                            schedule.getDayNumber(),
                            schedule.getTitle(),
                            schedule.getDescription(),
                            getScheduleDetails(schedule.getId())
                    )).collect(Collectors.toList());

            List<TourImageDTO> images = tourImageRepository.findByTourId(tour.getId()).stream()
                    .map(image -> new TourImageDTO(
                            image.getId(),
                            image.getTour().getId(),
                            image.getImageUrl(),
                            image.isFeatured()
                    )).collect(Collectors.toList());

            return new TourDTO(
                    tour.getId(),
                    tour.getName(),
                    tour.getDescription(),
                    tour.getPrice(),
                    tour.getDurationDays(),
                    tour.getDiscountPercent(),
                    tour.getCreatedAt(),
                    schedules,
                    images,
                    tour.getArea(),
                    tour.getTransportation(),
                    tour.getHotel()
            );
        }).collect(Collectors.toList());
    }
}