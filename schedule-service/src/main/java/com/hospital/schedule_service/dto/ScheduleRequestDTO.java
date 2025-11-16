package com.hospital.schedule_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

  @NotNull(message = "El ID del doctor es obligatorio")
  private String doctorDni;

  @NotNull(message = "El ID del empleado es obligatorio")
  private String employeeDni;

  @NotNull(message = "El ID de la especialidad es obligatorio")
  private Long specialtyId;

  @NotNull(message = "El ID del consultorio es obligatorio")
  private Long officeId;

  @NotNull(message = "La fecha es obligatoria")
  private LocalDate date;

  @NotNull(message = "La hora de inicio es obligatoria")
  private LocalTime startTime;

  @NotNull(message = "La hora de fin es obligatoria")
  private LocalTime endTime;

  @Builder.Default
  private Boolean status = true;
}
