package com.hospital.medical_history_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.medical_history_service.client.EmployeeServiceClient;
import com.hospital.medical_history_service.client.PatientServiceClient;
import com.hospital.medical_history_service.dto.EmployeeDTO;
import com.hospital.medical_history_service.dto.MedicalHistoryRequestDTO;
import com.hospital.medical_history_service.dto.MedicalHistoryResponseDTO;
import com.hospital.medical_history_service.dto.PatientDTO;
import com.hospital.medical_history_service.model.MedicalHistory;
import com.hospital.medical_history_service.repository.MedicalHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {
  private final MedicalHistoryRepository medicalHistoryRepository;
  private final PatientServiceClient patientServiceClient;
  private final EmployeeServiceClient employeeServiceClient;
  private final ModelMapper modelMapper;

  public MedicalHistoryResponseDTO createMedicalHistory(MedicalHistoryRequestDTO dto) {
    PatientDTO patient = patientServiceClient.getPatientById(dto.getPatientId());
    if (patient == null) {
      throw new IllegalArgumentException("Paciente no encontrado con ID: " + dto.getPatientId());
    }
    if (Boolean.FALSE.equals(patient.getStatus())) {
      throw new IllegalArgumentException("El paciente está deshabilitado");
    }

    EmployeeDTO employee = employeeServiceClient.getEmployeeByDni(dto.getEmployeeDni());
    if (employee == null) {
      throw new IllegalArgumentException("Empleado no encontrado con DNI: " + dto.getEmployeeDni());
    }
    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new IllegalArgumentException("El empleado está deshabilitado");
    }

    LocalDateTime now = LocalDateTime.now();
    MedicalHistory medicalHistory = MedicalHistory.builder()
        .patientId(dto.getPatientId())
        .employeeDni(dto.getEmployeeDni())
        .height(dto.getHeight())
        .weight(dto.getWeight())
        .createdAt(now)
        .updatedAt(now)
        .status(true)
        .build();

    @SuppressWarnings("null")
    MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(medicalHistory);

    MedicalHistoryResponseDTO response = modelMapper.map(savedMedicalHistory, MedicalHistoryResponseDTO.class);
    response.setPatient(patient);
    response.setEmployee(employee);
    response.setMedicalAttentions(null);

    return response;
  }

  public MedicalHistoryResponseDTO getMedicalHistoryByPatientId(Long patientId) {
    MedicalHistory medicalHistory = medicalHistoryRepository.findByPatientId(patientId)
        .orElseThrow(
            () -> new IllegalArgumentException("Historia médica no encontrada para el paciente con ID: " + patientId));
    return mapToResponseDTO(medicalHistory);
  }

  public MedicalHistoryResponseDTO getMedicalHistoryById(Long id) {
    @SuppressWarnings("null")
    MedicalHistory medicalHistory = medicalHistoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Historia médica no encontrada con ID: " + id));
    return mapToResponseDTO(medicalHistory);
  }

  public List<MedicalHistoryResponseDTO> getAllMedicalHistories() {
    List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAll();
    return medicalHistories.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<MedicalHistoryResponseDTO> getMedicalHistoriesByEmployeeDni(String employeeDni) {
    List<MedicalHistory> medicalHistories = medicalHistoryRepository.findByEmployeeDni(employeeDni);
    return medicalHistories.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<MedicalHistoryResponseDTO> getMedicalHistoriesByCreatedAtRange(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end) || start.isEqual(end)) {
      throw new IllegalArgumentException("La fecha/hora de inicio debe ser anterior a la de fin");
    }
    List<MedicalHistory> medicalHistories = medicalHistoryRepository.findByCreatedAtBetween(start, end);
    return medicalHistories.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<MedicalHistoryResponseDTO> getMedicalHistoriesByUpdatedAtRange(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end) || start.isEqual(end)) {
      throw new IllegalArgumentException("La fecha/hora de inicio debe ser anterior a la de fin");
    }
    List<MedicalHistory> medicalHistories = medicalHistoryRepository.findByUpdatedAtBetween(start, end);
    return medicalHistories.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public void disableMedicalHistory(Long id) {
    @SuppressWarnings("null")
    MedicalHistory medicalHistory = medicalHistoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Historia médica no encontrada con ID: " + id));
    medicalHistory.setStatus(false);
    medicalHistory.setUpdatedAt(LocalDateTime.now());
    medicalHistoryRepository.save(medicalHistory);
  }

  public void enableMedicalHistory(Long id) {
    @SuppressWarnings("null")
    MedicalHistory medicalHistory = medicalHistoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Historia médica no encontrada con ID: " + id));
    medicalHistory.setStatus(true);
    medicalHistory.setUpdatedAt(LocalDateTime.now());
    medicalHistoryRepository.save(medicalHistory);
  }

  private <T> T getServiceData(Supplier<T> serviceCall) {
    try {
      return serviceCall.get();
    } catch (Exception e) {
      return null;
    }
  }

  private MedicalHistoryResponseDTO mapToResponseDTO(MedicalHistory medicalHistory) {
    MedicalHistoryResponseDTO response = modelMapper.map(medicalHistory, MedicalHistoryResponseDTO.class);

    response.setPatient(getServiceData(
        () -> patientServiceClient.getPatientById(medicalHistory.getPatientId())));
    response.setEmployee(getServiceData(
        () -> employeeServiceClient.getEmployeeByDni(medicalHistory.getEmployeeDni())));
    response.setMedicalAttentions(null);

    return response;
  }

}
