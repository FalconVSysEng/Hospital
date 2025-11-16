package com.hospital.receipt_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
  PENDIENTE,
  PAGADO,
  ANULADO,
  FALLIDO
}
