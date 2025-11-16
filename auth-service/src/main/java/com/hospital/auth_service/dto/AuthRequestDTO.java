package com.hospital.auth_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

  @Column(unique = true, nullable = false, length = 8)
  @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 dígitos.")
  @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener solo 8 dígitos numéricos.")
  private String dni;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}