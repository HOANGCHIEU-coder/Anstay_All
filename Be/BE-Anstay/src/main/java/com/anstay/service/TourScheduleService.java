package com.anstay.service;

import com.anstay.dto.TourScheduleDTO;
import com.anstay.dto.TourScheduleDetailDTO;
import com.anstay.entity.Tour;
import com.anstay.entity.TourSchedule;
import com.anstay.entity.TourScheduleDetail;
import com.anstay.repository.TourRepository;
import com.anstay.repository.TourScheduleDetailRepository;
import com.anstay.repository.TourScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.anstay.entity.TourScheduleDetail;
import com.anstay.repository.TourScheduleDetailRepository;

@Service
public class TourScheduleService {

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Autowired
    private TourScheduleDetailRepository tourScheduleDetailRepository;

    @Autowired
    private TourRepository tourRepository;

    // 🟢 Lấy danh sách lịch trình theo tour_id (bao gồm chi tiết lịch trình)
    public List<TourScheduleDTO> getSchedulesByTourId(Integer tourId) {
        List<TourSchedule> schedules = tourScheduleRepository.findByTourId(tourId);
        return schedules.stream().map(schedule -> new TourScheduleDTO(
                schedule.getId(),
                schedule.getTour().getId(),
                schedule.getDayNumber(),
                schedule.getTitle(),
                schedule.getDescription(),
                getScheduleDetails(schedule.getId()) // Lấy danh sách chi tiết lịch trình
        )).collect(Collectors.toList());
    }

    // 🟢 Lấy danh sách chi tiết lịch trình theo schedule_id
    private List<TourScheduleDetailDTO> getScheduleDetails(Integer scheduleId) {
        List<TourScheduleDetail> details = tourScheduleDetailRepository.findByScheduleId(scheduleId);
        return details.stream().map(detail -> new TourScheduleDetailDTO(
                detail.getId(),
                detail.getTimeSlot(),
                detail.getDescription()
        )).collect(Collectors.toList());
    }

    // 🟢 Thêm lịch trình mới (chưa xử lý thêm chi tiết)
    public TourScheduleDTO addTourSchedule(TourScheduleDTO dto) {
        Optional<Tour> optionalTour = tourRepository.findById(dto.getTourId());
        if (optionalTour.isPresent()) {
            TourSchedule schedule = new TourSchedule();
            schedule.setTour(optionalTour.get());
            schedule.setDayNumber(dto.getDayNumber());
            schedule.setTitle(dto.getTitle());
            schedule.setDescription(dto.getDescription());

            TourSchedule savedSchedule = tourScheduleRepository.save(schedule);

            // 🟢 Thêm danh sách chi tiết lịch trình nếu có
            if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {
                List<TourScheduleDetail> details = dto.getDetails().stream().map(detailDTO -> {
                    TourScheduleDetail detail = new TourScheduleDetail();
                    detail.setSchedule(savedSchedule);
                    detail.setTimeSlot(detailDTO.getTimeSlot());
                    detail.setDescription(detailDTO.getDescription());
                    return detail;
                }).collect(Collectors.toList());
                tourScheduleDetailRepository.saveAll(details);
            }

            return new TourScheduleDTO(
                    savedSchedule.getId(),
                    savedSchedule.getTour().getId(),
                    savedSchedule.getDayNumber(),
                    savedSchedule.getTitle(),
                    savedSchedule.getDescription(),
                    getScheduleDetails(savedSchedule.getId()) // Lấy danh sách chi tiết sau khi lưu
            );
        }
        return null;
    }

    // 🟢 Xóa lịch trình theo ID (xóa luôn các chi tiết)
    @Transactional
    public boolean deleteTourSchedule(Integer id) {
        if (tourScheduleRepository.existsById(id)) {
            tourScheduleDetailRepository.deleteByScheduleId(id); // Xóa các chi tiết lịch trình
            tourScheduleRepository.deleteById(id); // Xóa lịch trình
            return true;
        }
        return false;
    }

    public TourScheduleDTO updateTourSchedule(Integer id, TourScheduleDTO dto) {
        Optional<TourSchedule> optionalSchedule = tourScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            TourSchedule schedule = optionalSchedule.get();

            // ❌ Không thay đổi ID
            // schedule.setId(dto.getTourId());

            // ✅ Nếu muốn thay đổi `tourId`, cần lấy entity `Tour` từ DB
            Tour tour = tourRepository.findById(dto.getTourId())
                    .orElseThrow(() -> new RuntimeException("Tour not found"));
            schedule.setTour(tour);

            schedule.setDayNumber(dto.getDayNumber());
            schedule.setTitle(dto.getTitle());
            schedule.setDescription(dto.getDescription());

            TourSchedule updatedSchedule = tourScheduleRepository.save(schedule);
            return new TourScheduleDTO(
                    updatedSchedule.getId(),
                    updatedSchedule.getTour().getId(),
                    updatedSchedule.getDayNumber(),
                    updatedSchedule.getTitle(),
                    updatedSchedule.getDescription(),
                    null
            );
        }
        return null;
    }


}
