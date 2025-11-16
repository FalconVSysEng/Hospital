package com.hospital.doctor_service.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDTO {

  @NotBlank(message = "El DNI es obligatorio")
  @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
  private String dni;

  private Set<Long> specialtyIds;
}