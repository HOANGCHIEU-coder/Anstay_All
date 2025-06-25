package com.anstay.service;

import com.anstay.dto.PointTransactionDTO;
import com.anstay.dto.UserPointDTO;
import com.anstay.entity.PointTransaction;
import com.anstay.entity.UserPoint;
import com.anstay.repository.PointTransactionRepository;
import com.anstay.repository.UserPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anstay.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPointService {
    private final UserPointRepository userPointRepository;
    private final PointTransactionRepository transactionRepository;

    public UserPointService(UserPointRepository userPointRepository, PointTransactionRepository transactionRepository) {
        this.userPointRepository = userPointRepository;
        this.transactionRepository = transactionRepository;
    }

    public UserPointDTO getUserPoints(Integer userId) {
        UserPoint userPoint = userPointRepository.findByUserId(userId)
                .orElse(new UserPoint(null, userId, 0, LocalDateTime.now()));
        return new UserPointDTO(userPoint.getId(), userPoint.getUserId(), userPoint.getPoints(), userPoint.getLastUpdated());
    }


    @Transactional
    public PointTransactionDTO addPointTransaction(PointTransactionDTO dto) {
        // Tạo giao dịch
        PointTransaction transaction = new PointTransaction(
                null, dto.getUserId(), dto.getTransactionType(), dto.getPoints(), dto.getDescription(), dto.getCreatedAt()
        );
        transaction = transactionRepository.save(transaction);

        // Cập nhật điểm thành viên
        UserPoint userPoint = userPointRepository.findByUserId(dto.getUserId()).orElse(new UserPoint(null, dto.getUserId(), 0, null));

        if (dto.getTransactionType() == TransactionType.earn) {
            userPoint.setPoints(userPoint.getPoints() + dto.getPoints());
        } else if (dto.getTransactionType() == TransactionType.redeem) {
            userPoint.setPoints(userPoint.getPoints() - dto.getPoints());
        }

        userPointRepository.save(userPoint);

        return new PointTransactionDTO(transaction.getUserId(), transaction.getTransactionType(), transaction.getPoints(), transaction.getDescription(), transaction.getCreatedAt());
    }

    public List<PointTransactionDTO> getPointTransactions(Integer userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(tran -> new PointTransactionDTO(tran.getUserId(), tran.getTransactionType(), tran.getPoints(), tran.getDescription(), tran.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
