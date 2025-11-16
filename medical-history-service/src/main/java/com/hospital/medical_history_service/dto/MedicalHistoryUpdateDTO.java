package com.hospital.medical_history_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryUpdateDTO {
  @DecimalMin(value = "0.01", message = "La altura debe ser mayor a 0")
  private BigDecimal height;
  
  @DecimalMin(value = "0.01", message = "El peso debe ser mayor a 0")
  private BigDecimal weight;
}