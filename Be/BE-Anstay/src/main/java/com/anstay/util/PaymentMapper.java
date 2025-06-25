package com.anstay.util;

import com.anstay.dto.PaymentDTO;
import com.anstay.entity.Payment;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;

        return new PaymentDTO(
                payment.getId(),
                payment.getBookingType(),
                payment.getBookingId(),
                payment.getUser() != null ? payment.getUser().getId() : null,
                payment.getUser() != null ? payment.getUser().getFullName() : null,
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getTransactionId(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getGuestName(),
                payment.getGuestPhone(),
                payment.getGuestEmail(),
                payment.getGuestIdentityNumber(),
                payment.getGuestBirthday(),
                payment.getGuestNationality()
        );
    }
}
