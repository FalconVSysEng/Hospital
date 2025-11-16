package com.hospital.receipt_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
  private Long id;
  private String name;
  private String lastname;
  private String dni;
  private String role;
  private Boolean isEnabled;
}
