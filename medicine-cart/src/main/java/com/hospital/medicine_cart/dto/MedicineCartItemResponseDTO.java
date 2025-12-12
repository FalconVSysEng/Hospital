package com.hospital.medicine_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineCartItemResponseDTO {
    private Long id;
    private MedicineDTO medicinePrescrip;
    private String dose;
    private String frequency;
    private String duration;
    private Integer quantity;
}
