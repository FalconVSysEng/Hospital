package com.hospital.medical_attention_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAttentionRequestDTO {
    @NotNull(message = "appointmentId es obligatorio")
    private Long appointmentId;

    @NotNull(message = "doctorDni es obligatorio")
    private String doctorDni;

    @NotNull(message = "medicalHistoryId es obligatorio")
    private Long medicalHistoryId;
}
