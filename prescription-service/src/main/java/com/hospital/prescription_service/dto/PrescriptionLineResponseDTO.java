package com.hospital.prescription_service.dto;
import com.hospital.prescription_service.model.PrescriptionLine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionLineResponseDTO {
    private Long id;
    private MedicineDTO prescription;
    private Long medicineCartId;
    private Long medicineId;
    private String dose;
    private String frequency;
    private String duration;
    private Integer quantity;
}
