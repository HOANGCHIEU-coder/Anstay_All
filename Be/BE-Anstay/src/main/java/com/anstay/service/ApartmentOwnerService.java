package com.anstay.service;

import com.anstay.dto.ApartmentOwnerDTO;
import com.anstay.entity.ApartmentOwner;
import com.anstay.repository.ApartmentOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentOwnerService {

    @Autowired
    private ApartmentOwnerRepository apartmentOwnerRepository;

    // Lấy danh sách tất cả chủ căn hộ
    public List<ApartmentOwnerDTO> getAllOwners() {
        List<ApartmentOwner> owners = apartmentOwnerRepository.findAll();
        return owners.stream()
                .map(owner -> new ApartmentOwnerDTO(
                        owner.getId(),
                        owner.getName(),
                        owner.getPhone(),
                        owner.getEmail(),
                        owner.getAddress()
                )).collect(Collectors.toList());
    }

    // Lấy chủ căn hộ theo ID
    public ApartmentOwnerDTO getOwnerById(Integer id) {
        Optional<ApartmentOwner> owner = apartmentOwnerRepository.findById(id);
        return owner.map(value -> new ApartmentOwnerDTO(
                value.getId(),
                value.getName(),
                value.getPhone(),
                value.getEmail(),
                value.getAddress()
        )).orElse(null);
    }

    // Thêm mới chủ căn hộ
    public ApartmentOwnerDTO createOwner(ApartmentOwnerDTO ownerDTO) {
        ApartmentOwner owner = new ApartmentOwner();
        owner.setName(ownerDTO.getName());
        owner.setPhone(ownerDTO.getPhone());
        owner.setEmail(ownerDTO.getEmail());
        owner.setAddress(ownerDTO.getAddress());

        ApartmentOwner savedOwner = apartmentOwnerRepository.save(owner);
        return new ApartmentOwnerDTO(
                savedOwner.getId(),
                savedOwner.getName(),
                savedOwner.getPhone(),
                savedOwner.getEmail(),
                savedOwner.getAddress()
        );
    }

    // Cập nhật chủ căn hộ
    public ApartmentOwnerDTO updateOwner(Integer id, ApartmentOwnerDTO ownerDTO) {
        Optional<ApartmentOwner> optionalOwner = apartmentOwnerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            ApartmentOwner owner = optionalOwner.get();
            owner.setName(ownerDTO.getName());
            owner.setPhone(ownerDTO.getPhone());
            owner.setEmail(ownerDTO.getEmail());
            owner.setAddress(ownerDTO.getAddress());

            ApartmentOwner updatedOwner = apartmentOwnerRepository.save(owner);
            return new ApartmentOwnerDTO(
                    updatedOwner.getId(),
                    updatedOwner.getName(),
                    updatedOwner.getPhone(),
                    updatedOwner.getEmail(),
                    updatedOwner.getAddress()
            );
        }
        return null;
    }

    // Xóa chủ căn hộ
    public boolean deleteOwner(Integer id) {
        if (apartmentOwnerRepository.existsById(id)) {
            apartmentOwnerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
