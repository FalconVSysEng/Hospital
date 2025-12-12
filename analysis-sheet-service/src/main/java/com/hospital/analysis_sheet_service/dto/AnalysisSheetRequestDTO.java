package com.hospital.analysis_sheet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisSheetRequestDTO {
    private String doctorDni;
    private Long attentionId;
    private String notes;
}
