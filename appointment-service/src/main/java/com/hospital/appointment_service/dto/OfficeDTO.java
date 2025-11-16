package com.hospital.appointment_service.dto;

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
}
