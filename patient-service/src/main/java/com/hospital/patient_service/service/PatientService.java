package com.hospital.patient_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.patient_service.dto.PatientRequestDTO;
import com.hospital.patient_service.dto.PatientResponseDTO;
import com.hospital.patient_service.exception.CustomException;
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
      throw new CustomException("El paciente con DNI: " + dto.getDni() + " ya existe.", HttpStatus.CONFLICT);
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
        .orElseThrow(() -> new CustomException("Paciente no encontrado con ID: " + id, HttpStatus.NOT_FOUND));

    return modelMapper.map(patient, PatientResponseDTO.class);
  }

  public PatientResponseDTO getPatientByDni(String dni) {
    Patient patient = patientRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Paciente no encontrado con DNI: " + dni, HttpStatus.NOT_FOUND));

    return modelMapper.map(patient, PatientResponseDTO.class);
  }

  @SuppressWarnings("null")
  public void disablePatient(Long id) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new CustomException("Paciente no encontrado con ID: " + id, HttpStatus.NOT_FOUND));

    patient.setStatus(false);
    patientRepository.save(patient);
  }

  @SuppressWarnings("null")
  public void enablePatient(Long id) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new CustomException("Paciente no encontrado con ID: " + id, HttpStatus.NOT_FOUND));

    patient.setStatus(true);
    patientRepository.save(patient);
  }
}