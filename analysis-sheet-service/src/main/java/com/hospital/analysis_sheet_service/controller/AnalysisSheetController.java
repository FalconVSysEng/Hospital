package com.hospital.analysis_sheet_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.analysis_sheet_service.dto.AnalysisSheetRequestDTO;
import com.hospital.analysis_sheet_service.dto.AnalysisSheetResponseDTO;
import com.hospital.analysis_sheet_service.service.AnalysisSheetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analysis-sheets")
@RequiredArgsConstructor
public class AnalysisSheetController {

    private final AnalysisSheetService sheetService;

    @PostMapping
    public ResponseEntity<AnalysisSheetResponseDTO> crearFicha(
            @Valid @RequestBody AnalysisSheetRequestDTO request) {

        AnalysisSheetResponseDTO response = sheetService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Obtener ficha por ID (con líneas)
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisSheetResponseDTO> obtenerFicha(@PathVariable Long id) {
        AnalysisSheetResponseDTO response = sheetService.obtener(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    // Listar fichas por atención médica
    @GetMapping("/attention/{attentionId}")
    public ResponseEntity<List<AnalysisSheetResponseDTO>> listarPorAtencion(@PathVariable Long attentionId) {
        List<AnalysisSheetResponseDTO> responseList = sheetService.listarPorAtencion(attentionId);
        return ResponseEntity.ok(responseList);
    }

    // Confirmar ficha = copiar líneas desde el carrito
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmarFicha(@PathVariable Long id) {
        sheetService.confirmar(id);
        return ResponseEntity.ok().build();
    }
}
