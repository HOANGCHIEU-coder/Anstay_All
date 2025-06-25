package com.anstay.controller;

import com.anstay.dto.PointTransactionDTO;
import com.anstay.dto.UserPointDTO;
import com.anstay.service.UserPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class UserPointController {
    private final UserPointService userPointService;

    public UserPointController(UserPointService userPointService) {
        this.userPointService = userPointService;
    }

    // Lấy điểm của người dùng
    @GetMapping("/{userId}")
    public ResponseEntity<UserPointDTO> getUserPoints(@PathVariable Integer userId) {
        return ResponseEntity.ok(userPointService.getUserPoints(userId));
    }

    // Thêm giao dịch điểm
    @PostMapping("/transactions")
    public ResponseEntity<PointTransactionDTO> addPointTransaction(@RequestBody PointTransactionDTO dto) {
        return ResponseEntity.ok(userPointService.addPointTransaction(dto));
    }

    // Lấy lịch sử giao dịch điểm
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<PointTransactionDTO>> getPointTransactions(@PathVariable Integer userId) {
        return ResponseEntity.ok(userPointService.getPointTransactions(userId));
    }
}
