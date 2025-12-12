package com.hospital.analysis_sheet_service.dto;

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
public class AnalysisSheetResponseDTO {
    private Long id;
    private MedicalAttentionDTO attention; 
    private DoctorDTO doctor;
    private String status; 
    private String notes;
    private LocalDateTime createdAt;
    private List<AnalysisSheetLineResponseDTO> lines;
}
