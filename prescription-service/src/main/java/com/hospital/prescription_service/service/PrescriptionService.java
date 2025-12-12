package com.hospital.prescription_service.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.prescription_service.client.DoctorServiceClient;
import com.hospital.prescription_service.client.MedicalAttentionServiceClient;
import com.hospital.prescription_service.client.MedicineCartServiceCliente;
import com.hospital.prescription_service.client.MedicineServiceClient;
import com.hospital.prescription_service.dto.DoctorDTO;
import com.hospital.prescription_service.dto.MedicalAttentionDTO;
import com.hospital.prescription_service.dto.MedicineCartItemDTO;
import com.hospital.prescription_service.dto.MedicineDTO;
import com.hospital.prescription_service.dto.PrescripctionRequestDTO;
import com.hospital.prescription_service.dto.PrescriptionLineResponseDTO;
import com.hospital.prescription_service.dto.PrescriptionResponseDTO;
import com.hospital.prescription_service.model.Prescription;
import com.hospital.prescription_service.model.PrescriptionLine;
import com.hospital.prescription_service.repository.PrescriptionLineRepository;
import com.hospital.prescription_service.repository.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescripRepo;
    private final PrescriptionLineRepository lineRepo;
    private final MedicineCartServiceCliente cartClient;
    private final MedicalAttentionServiceClient attentionClient;
    private final DoctorServiceClient doctorClient;
    private final MedicineServiceClient medicineClient;

    // Crear ficha de análisis
    public PrescriptionResponseDTO crear(PrescripctionRequestDTO request) {
        // Obtener doctor por DNI
        DoctorDTO doctor = doctorClient.getDoctorByDni(request.getDoctorDni());
        if (doctor == null) throw new IllegalArgumentException("Doctor no encontrado");

        // Validar existencia de la atención
        MedicalAttentionDTO att = attentionClient.getAttentionByIdSimple(request.getAttentionId());
        if (att == null) throw new IllegalArgumentException("Atención médica no encontrada");

        // Crear ficha
        Prescription sheet = Prescription.builder()
                .attentionId(att.getId())
                .doctorDni(request.getDoctorDni())
                .notes(request.getNotes())
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .build();

        Prescription saved = prescripRepo.save(sheet);

        return mapToResponseDTO(saved, att, doctor, Collections.emptyList());
    }

    // Obtener ficha con líneas
    public PrescriptionResponseDTO obtener(Long id) {
        Prescription sheet = prescripRepo.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Ficha no encontrada")
        );

        MedicalAttentionDTO attention = attentionClient.getAttentionByIdSimple(sheet.getAttentionId());
        DoctorDTO doctor = doctorClient.getDoctorByDni(sheet.getDoctorDni());

        List<PrescriptionLineResponseDTO> lines = lineRepo.findByPrescriptionId(sheet.getId())
                .stream()
                .map(this::mapLineToDTO)
                .toList();

        return mapToResponseDTO(sheet, attention, doctor, lines);
    }

    // Listar fichas por atención médica
    public List<PrescriptionResponseDTO> listarPorAtencion(Long attentionId) {
        return prescripRepo.findByAttentionId(attentionId).stream()
                .map(sheet -> {
                    MedicalAttentionDTO attention = attentionClient.getAttentionByIdSimple(sheet.getAttentionId());
                    DoctorDTO doctor = doctorClient.getDoctorByDni(sheet.getDoctorDni());
                    List<PrescriptionLineResponseDTO> lines = lineRepo.findByPrescriptionId(sheet.getId())
                            .stream()
                            .map(this::mapLineToDTO)
                            .toList();
                    return mapToResponseDTO(sheet, attention, doctor, lines);
                })
                .toList();
    }

    // Confirmar ficha copiando líneas desde el carrito y retornar ficha completa
    public PrescriptionResponseDTO confirmar(Long sheetId) {
        Prescription sheet = prescripRepo.findById(sheetId).orElseThrow(() -> 
            new IllegalArgumentException("Ficha no encontrada")
        );

        // Obtener ítems del carrito
        List<MedicineCartItemDTO> cart = cartClient.listar();

        // Copiar cada ítem a la tabla de líneas
        for (MedicineCartItemDTO item : cart) {
            PrescriptionLine line = PrescriptionLine.builder()
                    .PrescriptionId(sheetId)
                    .medicineId(item.getMedicinePrescrip().getId())
                    .dose(item.getDose())
                    .frequency(item.getFrequency())
                    .duration(item.getDuration())
                    .quantity(item.getQuantity())
                    .build();
            lineRepo.save(line);
        }

        // Limpiar carrito
        cartClient.limpiarCarrito();

        // Actualizar estado de la ficha
        sheet.setStatus("Completed");
        prescripRepo.save(sheet);

        // Retornar ficha completa
        return obtener(sheetId);
    }

    // Mapper de ficha
    private PrescriptionResponseDTO mapToResponseDTO(
            Prescription sheet,
            MedicalAttentionDTO attention,
            DoctorDTO doctor,
            List<PrescriptionLineResponseDTO> lines
    ) {
        return PrescriptionResponseDTO.builder()
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
    private PrescriptionLineResponseDTO mapLineToDTO(PrescriptionLine line) {
        MedicineDTO typeData;
        try {
            typeData = medicineClient.findById(line.getMedicineId());
        } catch (Exception e) {
            // Si no se encuentra, se devuelve un placeholder
            typeData = MedicineDTO.builder()
                    .id(line.getMedicineId())
                    .name("Desconocido")
                    .description("")
                    .concentration("")
                    .presentation("")
                    .status(false)
                    .build();
        } 
        //cporrregir esto
        return PrescriptionLineResponseDTO.builder()
                .id(line.getId())
                .prescription(typeData)
                .medicineCartId(line.getMedicineId())//Corregir
                .medicineId(line.getMedicineId())
                .dose(line.getDose())
                .quantity(line.getQuantity())
                .duration(line.getDuration())
                .frequency(line.getFrequency())
                .build();
    }
}
