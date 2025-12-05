package com.hospital.medical_attention_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAttentionResponseDTO {
    private AppointmentDTO appointment;
    private DoctorDTO doctor;
    private MedicalHistoryDTO medicalHistory;
}
