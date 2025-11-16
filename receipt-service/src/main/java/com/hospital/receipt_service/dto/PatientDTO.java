package com.hospital.receipt_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
  private Long id;
  private String dni;
  private String name;
  private String lastname;
  private String address;
  private String phone;
  private Boolean status;
  private LocalDate birthdate;
  private String gender;
}