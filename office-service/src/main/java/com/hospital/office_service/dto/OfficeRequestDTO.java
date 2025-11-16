package com.hospital.office_service.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeRequestDTO {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;
  @NotBlank(message = "La ubicacion es obligatoria")
  private String address;

  private Integer floor;
  private String officeNumber;

  @Builder.Default
  private Boolean status = true;

  @NotEmpty(message = "El Consultorio debe tener al menos una especialidad asignada.")
  private Set<Long> specialtyIds;
}
