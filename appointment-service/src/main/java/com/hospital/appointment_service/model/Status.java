package com.hospital.appointment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
  PENDIENTE,
  CANCELADO,
  COMPLETADO,
  NOASISTIO
}
