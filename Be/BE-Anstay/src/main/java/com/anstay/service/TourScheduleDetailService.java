package com.anstay.service;

import com.anstay.entity.TourSchedule;
import com.anstay.entity.TourScheduleDetail;
import com.anstay.repository.TourScheduleDetailRepository;
import com.anstay.repository.TourScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TourScheduleDetailService {

    private final TourScheduleDetailRepository repository;
    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    public TourScheduleDetailService(TourScheduleDetailRepository repository) {
        this.repository = repository;
    }

    public List<TourScheduleDetail> getAll() {
        return repository.findAll();
    }

    public Optional<TourScheduleDetail> getById(Integer id) {
        return repository.findById(id);
    }

    public TourScheduleDetail save(TourScheduleDetail detail, Integer scheduleId) {
        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy schedule với ID: " + scheduleId));

        detail.setSchedule(schedule); // Gán schedule vào TourScheduleDetail
        return repository.save(detail);
    }

//    public Optional<TourScheduleDetail> update(Integer id, TourScheduleDetail detail) {
//        return repository.findById(id).map(existing -> {
//            existing.setSchedule(detail.getSchedule());
//            existing.setTimeSlot(detail.getTimeSlot());
//            existing.setDescription(detail.getDescription());
//            return repository.save(existing);
//        });
//    }


    public TourScheduleDetail updateTourScheduleDetail(Integer id, Integer scheduleId, TourScheduleDetail updatedDetail) {
        // Tìm TourScheduleDetail cần cập nhật
        TourScheduleDetail existingDetail = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy TourScheduleDetail với id: " + id));

        // Tìm TourSchedule từ database
        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy TourSchedule với id: " + scheduleId));

        // Cập nhật dữ liệu
        existingDetail.setSchedule(schedule); // Gán schedule để tránh null
        existingDetail.setTimeSlot(updatedDetail.getTimeSlot());
        existingDetail.setDescription(updatedDetail.getDescription());

        // Lưu vào database
        return repository.save(existingDetail);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}