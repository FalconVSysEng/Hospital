package com.hospital.patient_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.patient_service.dto.PatientRequestDTO;
import com.hospital.patient_service.dto.PatientResponseDTO;
import com.hospital.patient_service.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @PostMapping
  public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
    PatientResponseDTO patient = patientService.createPatient(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(patient);
  }

  @GetMapping
  public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
    List<PatientResponseDTO> patients = patientService.getAllPatients();
    return ResponseEntity.ok(patients);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
    PatientResponseDTO patient = patientService.getPatientById(id);
    return ResponseEntity.ok(patient);
  }

  @GetMapping("/dni/{dni}")
  public ResponseEntity<PatientResponseDTO> getPatientByDni(@PathVariable String dni) {
    PatientResponseDTO patient = patientService.getPatientByDni(dni);
    return ResponseEntity.ok(patient);
  }

  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disablePatient(@PathVariable Long id) {
    patientService.disablePatient(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enablePatient(@PathVariable Long id) {
    patientService.enablePatient(id);
    return ResponseEntity.noContent().build();
  }

}