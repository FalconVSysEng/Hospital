package com.hospital.analysis_cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeAnalysisDTO {
    @NotNull(message = "El ID del tipo de análisis es obligatorio")
    private Long id;
    private String name;
    private String description;
    private Boolean status;
}
