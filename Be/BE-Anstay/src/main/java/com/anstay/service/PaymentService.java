package com.anstay.service;

import com.anstay.dto.CreatePaymentRequest;
import com.anstay.dto.PaymentDTO;
import com.anstay.entity.Payment;
import com.anstay.entity.User;
import com.anstay.entity.ApartmentBooking;
import com.anstay.enums.PaymentMethod;
import com.anstay.enums.PaymentStatus;
import com.anstay.enums.BookingStatus;
import com.anstay.repository.PaymentRepository;
import com.anstay.repository.UserRepository;
import com.anstay.repository.ApartmentBookingRepository;
import com.anstay.util.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApartmentBookingRepository apartmentBookingRepository;

    // ===== TẠO THANH TOÁN MOMO =====
    public String createMomoPayment(CreatePaymentRequest req) {
        Payment payment = new Payment();
        payment.setBookingType(req.getBookingType());
        payment.setBookingId(req.getBookingId());

        // Gán user nếu có userId
        if (req.getUserId() != null) {
            userRepository.findById(req.getUserId()).ifPresent(payment::setUser);
        } else {
            payment.setUser(null);
        }
        payment.setAmount(req.getAmount());
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);

        payment.setGuestName(req.getGuestName());
        payment.setGuestPhone(req.getGuestPhone());
        payment.setGuestEmail(req.getGuestEmail());
        payment.setGuestIdentityNumber(req.getGuestIdentityNumber());
        payment.setGuestBirthday(req.getGuestBirthday());
        payment.setGuestNationality(req.getGuestNationality());

        payment = paymentRepository.save(payment);

        // ==== Gọi Momo ====
        String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
        String partnerCode = "MOMO";
        String accessKey = "F8BBA842ECF85";
        String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
        String orderId = partnerCode + System.currentTimeMillis();
        String requestId = orderId;
        String orderInfo = "Thanh toán đơn #" + payment.getId();
        String redirectUrl = "http://localhost:5173";
        String ipnUrl = "http://localhost:8085/api/payments/momo/ipn";
        String amount = String.valueOf(payment.getAmount().longValue());
        String requestType = "captureWallet";
        String extraData = "";

        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                accessKey, amount, extraData, ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType
        );

        String signature = hmacSHA256(rawSignature, secretKey);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("partnerCode", partnerCode);
        payload.put("accessKey", accessKey);
        payload.put("requestId", requestId);
        payload.put("amount", amount);
        payload.put("orderId", orderId);
        payload.put("orderInfo", orderInfo);
        payload.put("redirectUrl", redirectUrl);
        payload.put("ipnUrl", ipnUrl);
        payload.put("lang", "vi");
        payload.put("extraData", extraData);
        payload.put("requestType", requestType);
        payload.put("signature", signature);

        String payUrl;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, entity, Map.class);
            Map<String, Object> resBody = response.getBody();
            payUrl = resBody != null && resBody.get("payUrl") != null ? resBody.get("payUrl").toString() : null;

            // Lưu orderId để đối soát
            payment.setTransactionId(orderId);
            paymentRepository.save(payment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gọi Momo lỗi: " + e.getMessage());
        }
        return payUrl;
    }

    // ===== XỬ LÝ IPN từ MOMO =====
    public void handleMomoIpn(Map<String, Object> notifyData) {
        String orderId = (String) notifyData.get("orderId");
        String resultCode = String.valueOf(notifyData.get("resultCode"));
        // 0: thanh toán thành công, còn lại là thất bại

        Optional<Payment> optional = paymentRepository.findByTransactionId(orderId);
        if (optional.isPresent()) {
            Payment payment = optional.get();
            if ("0".equals(resultCode)) {
                payment.setStatus(PaymentStatus.COMPLETED);
                paymentRepository.save(payment);

                // === Cập nhật trạng thái booking ===
                Integer bookingId = payment.getBookingId();
                if (bookingId != null) {
                    apartmentBookingRepository.findById(bookingId).ifPresent(booking -> {
                        booking.setStatus(BookingStatus.CONFIRMED); // hoặc BookingStatus.PAID nếu bố thích
                        apartmentBookingRepository.save(booking);
                    });
                }
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
            }
        }
    }

    // ===== LẤY 1 PAYMENT THEO ID =====
    public PaymentDTO getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return PaymentMapper.toDTO(payment);
    }

    // ===== LẤY DANH SÁCH PAYMENT =====
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByUserId(Integer userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== TỔNG DOANH THU HỆ THỐNG =====
    public Double getTotalRevenue() {
        Double total = paymentRepository.getTotalRevenue();
        return total != null ? total : 0.0;
    }

    // ===== DOANH THU THEO KHU VỰC (EXTRA - NẾU CẦN) =====
    public List<Object[]> getMonthlyRevenueByApartmentArea() {
        return paymentRepository.getMonthlyRevenueByApartmentArea();
    }

    public List<Object[]> getMonthlyRevenueByTourArea() {
        return paymentRepository.getMonthlyRevenueByTourArea();
    }
    public List<PaymentDTO> getPendingPayments() {
        return paymentRepository.findPendingPayments()
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== TOOL HMAC =====
    private String hmacSHA256(String data, String secretKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public PaymentDTO createCashPayment(CreatePaymentRequest request) {
        // Build entity từ request, paymentMethod = CASH
        Payment payment = new Payment();
        payment.setBookingType(request.getBookingType());
        payment.setBookingId(request.getBookingId());
        if (request.getUserId() != null) {
            userRepository.findById(request.getUserId()).ifPresent(payment::setUser);
        } else {
            payment.setUser(null);
        }
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(PaymentMethod.CASH); // Enum!
        payment.setStatus(PaymentStatus.PENDING); // hoặc COMPLETED nếu xác nhận luôn
        payment.setGuestName(request.getGuestName());
        payment.setGuestPhone(request.getGuestPhone());
        payment.setGuestEmail(request.getGuestEmail());
        payment.setGuestIdentityNumber(request.getGuestIdentityNumber());
        payment.setGuestBirthday(request.getGuestBirthday());
        payment.setGuestNationality(request.getGuestNationality());

        payment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(payment);
    }

    // Phần đơn hàng ( Chờ Xác nhận )
    public List<PaymentDTO> getPaymentsWithCheckInOut() {
        List<Payment> payments = paymentRepository.findPendingPayments();

        List<PaymentDTO> result = new ArrayList<>();

        for (Payment payment : payments) {
            ApartmentBooking booking = apartmentBookingRepository.findById(payment.getBookingId()).orElse(null);

            PaymentDTO dto = new PaymentDTO();
            dto.setId(payment.getId());
            dto.setBookingType(payment.getBookingType());
            dto.setAmount(payment.getAmount());
            dto.setStatus(payment.getStatus());
            dto.setCheckIn(booking != null ? booking.getCheckIn() : null);
            dto.setCheckOut(booking != null ? booking.getCheckOut() : null);

            dto.setGuestIdentityNumber(payment.getGuestIdentityNumber());
            dto.setGuestName(payment.getGuestName());
            dto.setGuestEmail(payment.getGuestEmail());
            dto.setGuestPhone(payment.getGuestPhone());

            result.add(dto);
        }

        return result;
    }

    //===Phần lọc trạng thái đơn hàng ===
    public List<PaymentDTO> getCompletedPayments() {
        List<Payment> payments = paymentRepository.findByStatus(PaymentStatus.COMPLETED);
        List<PaymentDTO> result = new ArrayList<>();

        for (Payment payment : payments) {
            // Lấy thông tin booking như method getPaymentsWithCheckInOut()
            ApartmentBooking booking = apartmentBookingRepository.findById(payment.getBookingId()).orElse(null);

            PaymentDTO dto = new PaymentDTO();
            dto.setId(payment.getId());
            dto.setBookingType(payment.getBookingType());
            dto.setAmount(payment.getAmount());
            dto.setStatus(payment.getStatus());

            // Thêm thông tin checkin/checkout (copy từ method getPaymentsWithCheckInOut)
            dto.setCheckIn(booking != null ? booking.getCheckIn() : null);
            dto.setCheckOut(booking != null ? booking.getCheckOut() : null);

            dto.setGuestIdentityNumber(payment.getGuestIdentityNumber());
            dto.setGuestName(payment.getGuestName());
            dto.setGuestEmail(payment.getGuestEmail());
            dto.setGuestPhone(payment.getGuestPhone());

            result.add(dto);
        }

        return result;
    }
}
