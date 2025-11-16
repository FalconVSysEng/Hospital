package com.hospital.auth_service.dto;

import com.hospital.auth_service.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
  private Long id;
  private String name;
  private String lastname;
  private String dni;
  private Role role;
  private Boolean isEnabled;
}
