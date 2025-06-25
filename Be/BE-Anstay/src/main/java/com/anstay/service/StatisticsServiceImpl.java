package com.anstay.service;


import com.anstay.dto.OrderStatisticsDTO;
import com.anstay.dto.RevenueStatisticsDTO;
import com.anstay.dto.UserStatisticsDTO;
import com.anstay.enums.BookingType;
import com.anstay.repository.ApartmentBookingRepository;
import com.anstay.repository.PaymentRepository;
import com.anstay.repository.TourBookingRepository;
import com.anstay.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final UserRepository userRepository;
    private final ApartmentBookingRepository apartmentBookingRepository;
    private final TourBookingRepository tourBookingRepository;
    private final PaymentRepository paymentRepository;

    public StatisticsServiceImpl(UserRepository userRepository,
                                 ApartmentBookingRepository apartmentBookingRepository,
                                 TourBookingRepository tourBookingRepository,
                                 PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.apartmentBookingRepository = apartmentBookingRepository;
        this.tourBookingRepository = tourBookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public UserStatisticsDTO getTotalUsers() {
        return new UserStatisticsDTO(userRepository.count());
    }

    @Override
    public OrderStatisticsDTO getTotalOrders() {
        long totalOrders = apartmentBookingRepository.countTotalBookings() + tourBookingRepository.countTotalBookings();
        return new OrderStatisticsDTO(totalOrders);
    }

    @Override
    public RevenueStatisticsDTO getTotalRevenue() {
        Double revenue = paymentRepository.getTotalRevenue();
        return new RevenueStatisticsDTO(revenue != null ? revenue : 0.0);
    }

//    @Override
//    public List<RevenueStatisticsDTO> getMonthlyRevenue() {
//        List<Object[]> results = paymentRepository.getMonthlyRevenue();
//        return results.stream()
//                .map(row -> new RevenueStatisticsDTO(
//                        ((Number) row[1]).doubleValue(),  // revenue
//                        (String) row[0]                   // month
//                ))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<RevenueStatisticsDTO> getMonthlyRevenue() {
        Map<String, Map<String, Double>> areaMonthlyRevenue = new HashMap<>();

        // Process apartment revenue
        List<Object[]> apartmentResults = paymentRepository.getMonthlyRevenueByApartmentArea();
        processMonthlyRevenue(apartmentResults, areaMonthlyRevenue);

        // Process tour revenue
        List<Object[]> tourResults = paymentRepository.getMonthlyRevenueByTourArea();
        processMonthlyRevenue(tourResults, areaMonthlyRevenue);

        return areaMonthlyRevenue.entrySet().stream()
                .map(entry -> {
                    RevenueStatisticsDTO dto = new RevenueStatisticsDTO(
                            entry.getValue().values().stream().mapToDouble(Double::doubleValue).toArray(),
                            entry.getKey()
                    );
                    // Set the period as the latest month
                    String latestMonth = entry.getValue().keySet().stream()
                            .sorted((a, b) -> b.compareTo(a))
                            .findFirst()
                            .orElse("");
                    dto.setPeriod(latestMonth);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private void processMonthlyRevenue(List<Object[]> results, Map<String, Map<String, Double>> areaMonthlyRevenue) {
        results.forEach(row -> {
            String area = (String) row[0];
            String month = (String) row[1];
            Double revenue = ((Number) row[2]).doubleValue();

            areaMonthlyRevenue
                    .computeIfAbsent(area, k -> new HashMap<>())
                    .merge(month, revenue, Double::sum);
        });
    }

    @Override
    public List<RevenueStatisticsDTO> getRevenueByAreaAndType() {
        Map<String, Double> tourRevenueMap = new HashMap<>();
        Map<String, Long> tourOrdersMap = new HashMap<>();
        Map<String, Double> apartmentRevenueMap = new HashMap<>();
        Map<String, Long> apartmentOrdersMap = new HashMap<>();
        List<RevenueStatisticsDTO> result = new ArrayList<>();

        // Process tour data
        paymentRepository.getRevenueAndOrdersByTourArea().forEach(row -> {
            String area = (String) row[0];
            Double revenue = ((Number) row[1]).doubleValue();
            Long orders = ((Number) row[2]).longValue();
            tourRevenueMap.put(area, revenue);
            tourOrdersMap.put(area, orders);
        });

        // Process apartment data
        paymentRepository.getRevenueAndOrdersByApartmentArea().forEach(row -> {
            String area = (String) row[0];
            Double revenue = ((Number) row[1]).doubleValue();
            Long orders = ((Number) row[2]).longValue();
            apartmentRevenueMap.put(area, revenue);
            apartmentOrdersMap.put(area, orders);
        });

        // Create DTOs for tours
        tourRevenueMap.forEach((area, revenue) -> {
            RevenueStatisticsDTO dto = new RevenueStatisticsDTO(revenue, area);
            dto.setType(BookingType.TOUR);
            dto.setTotalOrders(tourOrdersMap.getOrDefault(area, 0L));
            result.add(dto);
        });

        // Create DTOs for apartments
        apartmentRevenueMap.forEach((area, revenue) -> {
            RevenueStatisticsDTO dto = new RevenueStatisticsDTO(revenue, area);
            dto.setType(BookingType.APARTMENT);
            dto.setTotalOrders(apartmentOrdersMap.getOrDefault(area, 0L));
            result.add(dto);
        });

        return result;
    }
}
