package com.hospital.analysis_sheet_service.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.analysis_sheet_service.client.AnalysisCartServiceClient;
import com.hospital.analysis_sheet_service.client.DoctorServiceClient;
import com.hospital.analysis_sheet_service.client.MedicalAttentionServiceClient;
import com.hospital.analysis_sheet_service.client.TypeAnalysisServiceClient;
import com.hospital.analysis_sheet_service.dto.AnalysisCartItemDTO;
import com.hospital.analysis_sheet_service.dto.AnalysisSheetLineResponseDTO;
import com.hospital.analysis_sheet_service.dto.AnalysisSheetRequestDTO;
import com.hospital.analysis_sheet_service.dto.AnalysisSheetResponseDTO;
import com.hospital.analysis_sheet_service.dto.DoctorDTO;
import com.hospital.analysis_sheet_service.dto.MedicalAttentionDTO;
import com.hospital.analysis_sheet_service.dto.TypeAnalysisDTO;
import com.hospital.analysis_sheet_service.model.AnalysisSheet;
import com.hospital.analysis_sheet_service.model.AnalysisSheetLine;
import com.hospital.analysis_sheet_service.repository.AnalysisSheetLineRepository;
import com.hospital.analysis_sheet_service.repository.AnalysisSheetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisSheetService {

    private final AnalysisSheetRepository sheetRepo;
    private final AnalysisSheetLineRepository lineRepo;
    private final AnalysisCartServiceClient cartClient;
    private final MedicalAttentionServiceClient attentionClient;
    private final DoctorServiceClient doctorClient;
    private final TypeAnalysisServiceClient typeAnalysisClient;

    // Crear ficha de análisis
    public AnalysisSheetResponseDTO crear(AnalysisSheetRequestDTO request) {
        // Obtener doctor por DNI
        DoctorDTO doctor = doctorClient.getDoctorByDni(request.getDoctorDni());
        if (doctor == null) throw new IllegalArgumentException("Doctor no encontrado");

        // Validar existencia de la atención
        MedicalAttentionDTO att = attentionClient.getAttentionByIdSimple(request.getAttentionId());
        if (att == null) throw new IllegalArgumentException("Atención médica no encontrada");

        // Crear ficha
        AnalysisSheet sheet = AnalysisSheet.builder()
                .attentionId(att.getId())
                .doctorDni(request.getDoctorDni())
                .notes(request.getNotes())
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .build();

        AnalysisSheet saved = sheetRepo.save(sheet);

        return mapToResponseDTO(saved, att, doctor, Collections.emptyList());
    }

    // Obtener ficha con líneas
    public AnalysisSheetResponseDTO obtener(Long id) {
        AnalysisSheet sheet = sheetRepo.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Ficha no encontrada")
        );

        MedicalAttentionDTO attention = attentionClient.getAttentionByIdSimple(sheet.getAttentionId());
        DoctorDTO doctor = doctorClient.getDoctorByDni(sheet.getDoctorDni());

        List<AnalysisSheetLineResponseDTO> lines = lineRepo.findByAnalysisSheetId(sheet.getId())
                .stream()
                .map(this::mapLineToDTO)
                .toList();

        return mapToResponseDTO(sheet, attention, doctor, lines);
    }

    // Listar fichas por atención médica
    public List<AnalysisSheetResponseDTO> listarPorAtencion(Long attentionId) {
        return sheetRepo.findByAttentionId(attentionId).stream()
                .map(sheet -> {
                    MedicalAttentionDTO attention = attentionClient.getAttentionByIdSimple(sheet.getAttentionId());
                    DoctorDTO doctor = doctorClient.getDoctorByDni(sheet.getDoctorDni());
                    List<AnalysisSheetLineResponseDTO> lines = lineRepo.findByAnalysisSheetId(sheet.getId())
                            .stream()
                            .map(this::mapLineToDTO)
                            .toList();
                    return mapToResponseDTO(sheet, attention, doctor, lines);
                })
                .toList();
    }

    // Confirmar ficha copiando líneas desde el carrito y retornar ficha completa
    public AnalysisSheetResponseDTO confirmar(Long sheetId) {
        AnalysisSheet sheet = sheetRepo.findById(sheetId).orElseThrow(() -> 
            new IllegalArgumentException("Ficha no encontrada")
        );

        // Obtener ítems del carrito
        List<AnalysisCartItemDTO> cart = cartClient.listar();

        // Copiar cada ítem a la tabla de líneas
        for (AnalysisCartItemDTO item : cart) {
            AnalysisSheetLine line = AnalysisSheetLine.builder()
                    .analysisSheetId(sheetId)
                    .typeAnalysisId(item.getTypeAnalysis().getId())
                    .observations(item.getObservations())
                    .quantity(item.getQuantity())
                    .build();
            lineRepo.save(line);
        }

        // Limpiar carrito
        cartClient.limpiarCarrito();

        // Actualizar estado de la ficha
        sheet.setStatus("Completed");
        sheetRepo.save(sheet);

        // Retornar ficha completa
        return obtener(sheetId);
    }

    // Mapper de ficha
    private AnalysisSheetResponseDTO mapToResponseDTO(
            AnalysisSheet sheet,
            MedicalAttentionDTO attention,
            DoctorDTO doctor,
            List<AnalysisSheetLineResponseDTO> lines
    ) {
        return AnalysisSheetResponseDTO.builder()
                .id(sheet.getId())
                .attention(attention)
                .doctor(doctor)
                .status(sheet.getStatus())
                .notes(sheet.getNotes())
                .createdAt(sheet.getCreatedAt())
                .lines(lines)
                .build();
    }

    // Mapper de línea usando Feign client
    private AnalysisSheetLineResponseDTO mapLineToDTO(AnalysisSheetLine line) {
        TypeAnalysisDTO typeData;
        try {
            typeData = typeAnalysisClient.findById(line.getTypeAnalysisId());
        } catch (Exception e) {
            // Si no se encuentra, se devuelve un placeholder
            typeData = TypeAnalysisDTO.builder()
                    .id(line.getTypeAnalysisId())
                    .name("Desconocido")
                    .description("")
                    .status(false)
                    .build();
        }

        return AnalysisSheetLineResponseDTO.builder()
                .id(line.getId())
                .typeAnalysis(typeData)
                .observations(line.getObservations())
                .quantity(line.getQuantity())
                .build();
    }
}
