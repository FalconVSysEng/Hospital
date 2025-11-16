package com.hospital.receipt_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentDTO {
  private Long id;
  private ScheduleDTO schedule;
  private PatientDTO patient;
  private LocalDateTime solicitationDateTime;
  private BigDecimal finalCost;
}
