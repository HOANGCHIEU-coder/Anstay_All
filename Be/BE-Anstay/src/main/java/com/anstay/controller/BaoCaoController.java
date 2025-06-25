package com.anstay.controller;

import com.anstay.dto.BaoCaoResponse;
import com.anstay.dto.LoginRequest;
import com.anstay.service.BaoCaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BaoCaoController {

    @Autowired
    private BaoCaoService baoCaoService;

    @PostMapping("/api/baocao")
    public BaoCaoResponse getBaoCao(@RequestBody LoginRequest request) {
        if (isAuthorized(request)) {
            return baoCaoService.getBaoCao();
        } else {
            // Trả về lỗi 401 đúng chuẩn RESTful API
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    // Hàm xác thực riêng, có thể mở rộng/check nhiều user
    private boolean isAuthorized(LoginRequest request) {
        return "ANSTAY".equals(request.getUserName()) && "An1234567!".equals(request.getPassWord());
    }
}
