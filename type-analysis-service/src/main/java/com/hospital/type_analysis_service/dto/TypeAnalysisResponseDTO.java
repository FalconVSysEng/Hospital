package com.hospital.type_analysis_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeAnalysisResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean status;
}
