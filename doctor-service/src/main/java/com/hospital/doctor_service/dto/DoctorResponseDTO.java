package com.hospital.doctor_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
  private Long id; // de la tabla de este microservicio
  private String dni;
  private String codigo;
  private String name;
  private String lastname;
  private List<SpecialtyDTO> specialties;
}