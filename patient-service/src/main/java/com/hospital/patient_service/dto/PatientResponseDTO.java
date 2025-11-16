package com.hospital.patient_service.dto;

import java.time.LocalDate;

import com.hospital.patient_service.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
  private Long id;
  private String dni;
  private String name;
  private String lastname;
  private String address;
  private String phone;
  private Boolean status;
  private LocalDate birthdate;
  private Gender gender;
}