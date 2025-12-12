package com.hospital.analysis_sheet_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.analysis_sheet_service.dto.TypeAnalysisDTO;

@FeignClient(name = "type-analysis-service")
public interface TypeAnalysisServiceClient {
    @GetMapping("/type-analysis/{id}")
    TypeAnalysisDTO findById(@PathVariable("id") Long typeAnalysisId);
}
