package com.hospital.employee_service.dto;

import java.util.Set;

import com.hospital.employee_service.model.Role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeRequestDTO {

  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "El apellido es obligatorio")
  private String lastname;

  @NotBlank(message = "El DNI es obligatorio")
  @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
  private String dni;

  @NotNull(message = "El rol es obligatorio")
  private Role role;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

  @Valid
  private Set<Long> specialtyIds;
}