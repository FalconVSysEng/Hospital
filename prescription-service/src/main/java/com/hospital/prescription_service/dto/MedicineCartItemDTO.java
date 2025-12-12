package com.hospital.prescription_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineCartItemDTO {
    private Long id;
    private MedicineDTO medicinePrescrip;
    private Long prescriptionId;
    private String dose;
    private String frequency;
    private String duration;
    private Integer quantity;
}