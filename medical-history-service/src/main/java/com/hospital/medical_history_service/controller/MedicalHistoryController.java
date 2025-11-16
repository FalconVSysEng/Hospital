package com.hospital.medical_history_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.medical_history_service.dto.MedicalHistoryRequestDTO;
import com.hospital.medical_history_service.dto.MedicalHistoryResponseDTO;
import com.hospital.medical_history_service.service.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical-histories")
@RequiredArgsConstructor
public class MedicalHistoryController {

  private final MedicalHistoryService medicalHistoryService;

  @PostMapping
  public ResponseEntity<MedicalHistoryResponseDTO> createMedicalHistory(
      @Valid @RequestBody MedicalHistoryRequestDTO dto) {
    MedicalHistoryResponseDTO medicalHistory = medicalHistoryService.createMedicalHistory(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(medicalHistory);
  }

  @GetMapping
  public ResponseEntity<List<MedicalHistoryResponseDTO>> getAllMedicalHistories() {
    List<MedicalHistoryResponseDTO> medicalHistories = medicalHistoryService.getAllMedicalHistories();
    return ResponseEntity.ok(medicalHistories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MedicalHistoryResponseDTO> getMedicalHistoryById(@PathVariable Long id) {
    MedicalHistoryResponseDTO medicalHistory = medicalHistoryService.getMedicalHistoryById(id);
    return ResponseEntity.ok(medicalHistory);
  }

  @GetMapping("/patient/{patientId}")
  public ResponseEntity<MedicalHistoryResponseDTO> getMedicalHistoryByPatientId(@PathVariable Long patientId) {
    MedicalHistoryResponseDTO medicalHistory = medicalHistoryService.getMedicalHistoryByPatientId(patientId);
    return ResponseEntity.ok(medicalHistory);
  }

  @GetMapping("/employee/{employeeDni}")
  public ResponseEntity<List<MedicalHistoryResponseDTO>> getMedicalHistoriesByEmployeeDni(
      @PathVariable String employeeDni) {
    List<MedicalHistoryResponseDTO> medicalHistories = medicalHistoryService
        .getMedicalHistoriesByEmployeeDni(employeeDni);
    return ResponseEntity.ok(medicalHistories);
  }

  @GetMapping("/created-at-range/{start}/{end}")
  public ResponseEntity<List<MedicalHistoryResponseDTO>> getMedicalHistoriesByCreatedAtRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    List<MedicalHistoryResponseDTO> medicalHistories = medicalHistoryService
        .getMedicalHistoriesByCreatedAtRange(start, end);
    return ResponseEntity.ok(medicalHistories);
  }

  @GetMapping("/updated-at-range/{start}/{end}")
  public ResponseEntity<List<MedicalHistoryResponseDTO>> getMedicalHistoriesByUpdatedAtRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    List<MedicalHistoryResponseDTO> medicalHistories = medicalHistoryService
        .getMedicalHistoriesByUpdatedAtRange(start, end);
    return ResponseEntity.ok(medicalHistories);
  }

  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disableMedicalHistory(@PathVariable Long id) {
    medicalHistoryService.disableMedicalHistory(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enableMedicalHistory(@PathVariable Long id) {
    medicalHistoryService.enableMedicalHistory(id);
    return ResponseEntity.noContent().build();
  }

}