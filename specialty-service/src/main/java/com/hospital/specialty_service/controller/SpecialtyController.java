package com.hospital.specialty_service.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.specialty_service.dto.SpecialtyRequestDTO;
import com.hospital.specialty_service.dto.SpecialtyResponseDTO;
import com.hospital.specialty_service.service.SpecialtyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

  private final SpecialtyService specialtyService;

  @PostMapping
  public ResponseEntity<SpecialtyResponseDTO> createSpecialty(@Valid @RequestBody SpecialtyRequestDTO requestDTO) {
    SpecialtyResponseDTO newSpecialty = specialtyService.createSpecialty(requestDTO);
    return new ResponseEntity<>(newSpecialty, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<SpecialtyResponseDTO>> getAllSpecialties() {
    List<SpecialtyResponseDTO> specialties = specialtyService.findAllSpecialties();
    return ResponseEntity.ok(specialties);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SpecialtyResponseDTO> getSpecialtyById(@PathVariable Long id) {
    SpecialtyResponseDTO specialty = specialtyService.findSpecialtyById(id);
    return ResponseEntity.ok(specialty);
  }

  @PostMapping("/exists")
  public ResponseEntity<Set<Long>> getExistingSpecialtyIds(@RequestBody Set<Long> requestedIds) {
    Set<Long> existingIds = specialtyService.getExistingSpecialtyIds(requestedIds);
    return ResponseEntity.ok(existingIds);
  }

  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disableSpecialty(@PathVariable Long id) {
    specialtyService.disableSpecialty(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enableSpecialty(@PathVariable Long id) {
    specialtyService.enableSpecialty(id);
    return ResponseEntity.noContent().build();
  }
}