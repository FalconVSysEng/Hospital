package com.hospital.patient_service.dto;

import java.time.LocalDate;

import com.hospital.patient_service.model.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {

  @NotBlank(message = "El DNI es obligatorio")
  @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
  private String dni;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
  private String name;

  @NotBlank(message = "El apellido es obligatorio")
  @Size(max = 255, message = "El apellido no puede exceder los 255 caracteres")
  private String lastname;

  @Size(max = 500, message = "La dirección no puede exceder los 500 caracteres")
  private String address;

  @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
  private String phone;

  @NotNull(message = "La fecha de nacimiento es obligatoria")
  @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
  private LocalDate birthdate;

  @Builder.Default
  private Boolean status = true;

  @NotNull(message = "El campo genero es obligatorio")
  private Gender gender;
}