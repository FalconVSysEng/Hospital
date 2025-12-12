package com.hospital.analysis_sheet_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.analysis_sheet_service.dto.AnalysisCartItemDTO;
@FeignClient(name = "analysis-cart")
public interface AnalysisCartServiceClient {
    @GetMapping("/analysis-cart/listar")
    List<AnalysisCartItemDTO> listar();

    @DeleteMapping("/analysis-cart/nuevo")
    void limpiarCarrito();
}
