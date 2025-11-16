package com.hospital.specialty_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyResponseDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal cost;
  private Boolean status;
}