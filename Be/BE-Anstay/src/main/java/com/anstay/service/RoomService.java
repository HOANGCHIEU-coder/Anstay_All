package com.anstay.service;

import com.anstay.dto.RoomDTO;
import com.anstay.entity.Room;
import com.anstay.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(this::convertToDTO).orElse(null);
    }

    public RoomDTO createRoom(RoomDTO dto) {
        Room room = convertToEntity(dto);
        room = roomRepository.save(room);
        return convertToDTO(room);
    }

    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Optional<Room> opt = roomRepository.findById(id);
        if (opt.isPresent()) {
            Room room = opt.get();
            room.setApartmentId(dto.getApartmentId());
            room.setName(dto.getName());
            room.setDescription(dto.getDescription());
            room.setCapacity(dto.getCapacity());
            room.setPrice(dto.getPrice());
            room.setMaxRooms(dto.getMaxRooms());
            room.setMaxAdults(dto.getMaxAdults());
            room.setMaxChildren(dto.getMaxChildren());
            room = roomRepository.save(room);
            return convertToDTO(room);
        }
        return null;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    // Helper methods to convert between DTO and Entity
    private RoomDTO convertToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setApartmentId(room.getApartmentId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setCapacity(room.getCapacity());
        dto.setPrice(room.getPrice());
        dto.setMaxRooms(room.getMaxRooms());
        dto.setMaxAdults(room.getMaxAdults());
        dto.setMaxChildren(room.getMaxChildren());
        return dto;
    }

    private Room convertToEntity(RoomDTO dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setApartmentId(dto.getApartmentId());
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        room.setCapacity(dto.getCapacity());
        room.setPrice(dto.getPrice());
        room.setMaxRooms(dto.getMaxRooms());
        room.setMaxAdults(dto.getMaxAdults());
        room.setMaxChildren(dto.getMaxChildren());
        room.setDiscount(dto.getDiscount());
        return room;
    }

    public List<RoomDTO> getRoomsByApartmentId(Long apartmentId) {
        return roomRepository.findRoomDTOByApartmentId(apartmentId);
    }
}
