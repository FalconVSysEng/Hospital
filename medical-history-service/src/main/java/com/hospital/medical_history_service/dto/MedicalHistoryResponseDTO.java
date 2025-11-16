package com.hospital.medical_history_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryResponseDTO {
  private Long id;
  private PatientDTO patient;
  private EmployeeDTO employee;
  private BigDecimal height;
  private BigDecimal weight;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean status;
  private List<MedicalAttentionDTO> medicalAttentions;
}
