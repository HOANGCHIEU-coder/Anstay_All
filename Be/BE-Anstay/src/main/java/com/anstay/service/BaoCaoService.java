package com.anstay.service;

import com.anstay.dto.BaoCaoResponse;
import com.anstay.repository.RoomRepository;
import com.anstay.repository.UserRepository;
import com.anstay.repository.ApartmentBookingRepository;
import com.anstay.repository.PaymentRepository;
import com.anstay.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class BaoCaoService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApartmentBookingRepository apartmentBookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public BaoCaoResponse getBaoCao() {
        BaoCaoResponse response = new BaoCaoResponse();

        // Chuyển LocalDate thành java.sql.Date
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        Date sqlDate = Date.valueOf(thirtyDaysAgo);

        // Tính toán số phòng mới
        response.setSoSanPhamMoi(roomRepository.countNewRoomsLastMonth(sqlDate));



        // Tính toán tổng số sản phẩm
        response.setTongSoSanPham(roomRepository.count());

        // Tính toán số người bán mới
        response.setSoNguoiBanMoi(userRepository.countNewAdminsAndSuperAdminsLastMonth(sqlDate));


        response.setSoNguoiBan(userRepository.countAdminsAndSuperAdmins());
        // Tính toán số giao dịch
        response.setSoLuongGiaoDich(apartmentBookingRepository.count());

        // Tính toán số đơn hàng thành công (CONFIRMED)
        response.setTongSoDonHangThanhCong(apartmentBookingRepository.countByStatus(BookingStatus.CONFIRMED));

        // Trả ra số cứng cho đơn hàng không thành công (CANCELLED + EXPIRED)
        response.setTongSoDongHangKhongThanhCong(0);  // Giả sử trả ra số cứng là 50

        // Tính toán tổng giá trị giao dịch
        Long tongGiaTriGiaoDich = paymentRepository.sumAmountForPaidStatus();
        response.setTongGiaTriGiaoDich(tongGiaTriGiaoDich != null ? tongGiaTriGiaoDich : 0L);

        return response;
    }
}
