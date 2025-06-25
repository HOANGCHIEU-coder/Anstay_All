package com.anstay.service;

import com.anstay.dto.ApartmentImageDTO;
import com.anstay.entity.Apartment;
import com.anstay.entity.ApartmentImage;
import com.anstay.repository.ApartmentImageRepository;
import com.anstay.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentImageService {

    @Autowired
    private ApartmentImageRepository apartmentImageRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    // Lấy tất cả ảnh của một căn hộ
    public List<ApartmentImageDTO> getImagesByApartmentId(Integer apartmentId) {
        List<ApartmentImage> images = apartmentImageRepository.findByApartment_Id(apartmentId);
        return images.stream()
                .map(image -> new ApartmentImageDTO(
                        image.getId(),
                        image.getApartment().getId(),
                        image.getImageUrl(),
                        image.isFeatured()
                ))
                .collect(Collectors.toList());
    }

    // Lấy ảnh theo ID
    public ApartmentImageDTO getImageById(Integer id) {
        Optional<ApartmentImage> image = apartmentImageRepository.findById(id);
        return image.map(value -> new ApartmentImageDTO(
                value.getId(),
                value.getApartment().getId(),
                value.getImageUrl(),
                value.isFeatured()
        )).orElse(null);
    }

    // Tạo ảnh mới
    public ApartmentImageDTO createImage(ApartmentImageDTO imageDTO) {
        Optional<Apartment> apartment = apartmentRepository.findById(imageDTO.getApartmentId());
        if (apartment.isEmpty()) {
            return null;
        }

        ApartmentImage image = new ApartmentImage(
                apartment.get(),
                imageDTO.getImageUrl(),
                imageDTO.isFeatured()
        );

        ApartmentImage savedImage = apartmentImageRepository.save(image);
        return new ApartmentImageDTO(
                savedImage.getId(),
                savedImage.getApartment().getId(),
                savedImage.getImageUrl(),
                savedImage.isFeatured()
        );
    }

    // Cập nhật ảnh
    public ApartmentImageDTO updateImage(Integer id, ApartmentImageDTO imageDTO) {
        Optional<ApartmentImage> optionalImage = apartmentImageRepository.findById(id);
        if (optionalImage.isPresent()) {
            ApartmentImage image = optionalImage.get();
            image.setImageUrl(imageDTO.getImageUrl());
            image.setFeatured(imageDTO.isFeatured());

            ApartmentImage updatedImage = apartmentImageRepository.save(image);
            return new ApartmentImageDTO(
                    updatedImage.getId(),
                    updatedImage.getApartment().getId(),
                    updatedImage.getImageUrl(),
                    updatedImage.isFeatured()
            );
        }
        return null;
    }

    // Xóa ảnh
    public boolean deleteImage(Integer id) {
        if (apartmentImageRepository.existsById(id)) {
            apartmentImageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
