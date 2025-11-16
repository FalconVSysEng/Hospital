package com.hospital.specialty_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyRequestDTO {
  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String name;

  @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
  private String description;

  @NotNull(message = "El costo es obligatorio")
  @DecimalMin(value = "0.01", message = "El costo debe ser mayor a 0")
  private BigDecimal cost;

  @Builder.Default
  private Boolean status = true;
}
