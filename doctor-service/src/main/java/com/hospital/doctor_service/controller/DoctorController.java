package com.hospital.doctor_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.doctor_service.dto.DoctorRequestDTO;
import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

  private final DoctorService doctorService;

  @PostMapping
  public ResponseEntity<Void> createDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
    doctorService.createDoctor(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
    List<DoctorResponseDTO> doctors = doctorService.getAllDoctors();
    return ResponseEntity.ok(doctors);
  }

  @GetMapping("/dni/{dni}")
  public ResponseEntity<DoctorResponseDTO> getDoctorByDni(@PathVariable String dni) {
    DoctorResponseDTO doctor = doctorService.getDoctorByDni(dni);
    return ResponseEntity.ok(doctor);
  }

  @GetMapping("/specialty/{specialtyId}")
  public ResponseEntity<List<DoctorResponseDTO>> getDoctorsBySpecialtyId(@PathVariable Long specialtyId) {
    List<DoctorResponseDTO> doctors = doctorService.getDoctorsBySpecialtyId(specialtyId);
    return ResponseEntity.ok(doctors);
  }
}