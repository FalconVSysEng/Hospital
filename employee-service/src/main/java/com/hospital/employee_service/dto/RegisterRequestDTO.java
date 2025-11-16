package com.hospital.employee_service.dto;

import com.hospital.employee_service.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
  private String dni;
  private Role role;
  private String password;
}
