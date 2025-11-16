package com.hospital.schedule_service.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO {
  private Long id;
  private String name;
  private String address;
  private Integer floor;
  private String officeNumber;
  private Boolean status;
  private Set<Long> specialtyIds;
}
