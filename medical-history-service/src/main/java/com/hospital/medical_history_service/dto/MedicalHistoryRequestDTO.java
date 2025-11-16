package com.hospital.medical_history_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryRequestDTO {
  @NotNull(message = "La ID del paciente es obligatorio")
  private Long patientId;

  @NotBlank(message = "El dni del empleado es obligatorio")
  private String employeeDni;

  @DecimalMin(value = "0.01", message = "La altura debe ser mayor a 0")
  private BigDecimal height;

  @DecimalMin(value = "0.01", message = "El peso debe ser mayor a 0")
  private BigDecimal weight;

  @Builder.Default
  private Boolean status = true;
}
