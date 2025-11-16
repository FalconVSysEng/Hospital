package com.hospital.employee_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN,
  DOCTOR,
  RECEPCIONISTA,
  ENFERMERA,
  CAJERO
}
