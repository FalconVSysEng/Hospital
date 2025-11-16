package com.hospital.receipt_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
  private Long id;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private Boolean status;
}
