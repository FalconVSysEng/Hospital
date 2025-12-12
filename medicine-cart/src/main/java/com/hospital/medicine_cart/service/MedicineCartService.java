package com.hospital.medicine_cart.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.hospital.medicine_cart.client.MedicineServiceClient;
import com.hospital.medicine_cart.dto.MedicineCartItemRequestDTO;
import com.hospital.medicine_cart.dto.MedicineCartItemResponseDTO;
import com.hospital.medicine_cart.dto.MedicineDTO;
import com.hospital.medicine_cart.model.MedicineCartItem;
import com.hospital.medicine_cart.repository.MedicineCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineCartService {
    private final MedicineCartRepository repo;
    private final MedicineServiceClient client;


    public MedicineCartItemResponseDTO agregar(MedicineCartItemRequestDTO dto) {

        // Validaciones mínimas y necesarias
        if (dto.getMedicineId() == null || dto.getMedicineId() <= 0) {
            throw new IllegalArgumentException("El MedicineId es obligatorio.");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            dto.setQuantity(1);
        }
        MedicineCartItem item = MedicineCartItem.builder()
                .medicineId(dto.getMedicineId())
                .dose(dto.getDose())
                .frequency(dto.getFrequency())
                .duration(dto.getDuration())
                .quantity(dto.getQuantity())
                .build();

        MedicineCartItem saved = repo.save(item);

        MedicineDTO typeData =
                client.findById(saved.getMedicineId());
        return MedicineCartItemResponseDTO.builder()
                .id(saved.getId())
                .medicinePrescrip(typeData)
                .dose(saved.getDose())
                .frequency(saved.getFrequency())
                .duration(saved.getDuration())
                .quantity(saved.getQuantity())
                .build();
    }

    public void quitar(Long id) {
        repo.deleteById(id);
    }

    public List<MedicineCartItemResponseDTO> listar() {

        return repo.findAll()
                .stream()
                .map(item -> {
                    MedicineDTO typeData =
                            client.findById(item.getMedicineId());

                    return MedicineCartItemResponseDTO.builder()
                            .id(item.getId())
                            .medicinePrescrip(typeData)
                            .dose(item.getDose())
                            .frequency(item.getFrequency())
                            .duration(item.getDuration())
                            .quantity(item.getQuantity())
                            .build();
                })
                .toList();
    }



    public void nuevo() {
        repo.deleteAll();
    }
}
