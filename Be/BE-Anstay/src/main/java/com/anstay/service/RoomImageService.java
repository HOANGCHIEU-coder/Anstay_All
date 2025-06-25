package com.anstay.service;

import com.anstay.dto.RoomImageDTO;
import com.anstay.entity.RoomImage;
import com.anstay.repository.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomImageService {
    @Autowired
    private RoomImageRepository roomImageRepository;

    public List<RoomImageDTO> getImagesByRoomId(Long roomId) {
        return roomImageRepository.findByRoomId(roomId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RoomImageDTO convertToDTO(RoomImage entity) {
        RoomImageDTO dto = new RoomImageDTO();
        dto.setId(entity.getId());
        dto.setRoomId(entity.getRoomId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setAltText(entity.getAltText());
        return dto;
    }
}
