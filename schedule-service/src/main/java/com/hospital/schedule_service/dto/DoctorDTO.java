package com.hospital.schedule_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
  private Long id;
  private String dni;
  private String name;
  private String lastname;
  private String codigo;
  private List<SpecialtyDTO> specialties;
}
