package com.hospital.patient_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.patient_service.dto.PatientRequestDTO;
import com.hospital.patient_service.dto.PatientResponseDTO;
import com.hospital.patient_service.model.Patient;
import com.hospital.patient_service.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

  private final PatientRepository patientRepository;
  private final ModelMapper modelMapper;

  public PatientResponseDTO createPatient(PatientRequestDTO dto) {
    if (patientRepository.findByDni(dto.getDni()).isPresent()) {
      throw new IllegalArgumentException("El paciente con DNI: " + dto.getDni() + " ya existe.");
    }

    Patient patient = modelMapper.map(dto, Patient.class);
    patient.setStatus(true);

    Patient savedPatient = patientRepository.save(patient);
    return modelMapper.map(savedPatient, PatientResponseDTO.class);
  }

  public List<PatientResponseDTO> getAllPatients() {
    return patientRepository.findAll().stream()
        .map(patient -> modelMapper.map(patient, PatientResponseDTO.class))
        .collect(Collectors.toList());
  }

  @SuppressWarnings("null")
  public PatientResponseDTO getPatientById(Long id) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + id));

    return modelMapper.map(patient, PatientResponseDTO.class);
  }

  public PatientResponseDTO getPatientByDni(String dni) {
    Patient patient = patientRepository.findByDni(dni)
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con DNI: " + dni));

    return modelMapper.map(patient, PatientResponseDTO.class);
  }

  @SuppressWarnings("null")
  public void disablePatient(Long id) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + id));

    patient.setStatus(false);
    patientRepository.save(patient);
  }

  @SuppressWarnings("null")
  public void enablePatient(Long id) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + id));

    patient.setStatus(true);
    patientRepository.save(patient);
  }
}