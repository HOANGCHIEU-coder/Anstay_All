package com.anstay.repository;

import com.anstay.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findByRoomId(Long roomId);
}
