package com.anstay.controller;

import com.anstay.dto.RoomImageDTO;
import com.anstay.service.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-images")
public class RoomImageController {
    @Autowired
    private RoomImageService roomImageService;

    // GET /api/room-images/room/{roomId}
    @GetMapping("/room/{roomId}")
    public List<RoomImageDTO> getImagesByRoomId(@PathVariable Long roomId) {
        return roomImageService.getImagesByRoomId(roomId);
    }
}
