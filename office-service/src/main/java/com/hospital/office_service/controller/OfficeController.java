package com.hospital.office_service.controller;

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

import com.hospital.office_service.dto.OfficeRequestDTO;
import com.hospital.office_service.dto.OfficeResponseDTO;
import com.hospital.office_service.service.OfficeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficeController {

  private final OfficeService officeService;

  @PostMapping
  public ResponseEntity<OfficeResponseDTO> createOffice(@Valid @RequestBody OfficeRequestDTO dto) {
    OfficeResponseDTO office = officeService.createOffice(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(office);
  }

  @GetMapping
  public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
    List<OfficeResponseDTO> offices = officeService.getAllOffices();
    return ResponseEntity.ok(offices);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable Long id) {
    OfficeResponseDTO office = officeService.getOfficeById(id);
    return ResponseEntity.ok(office);
  }

  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disableOffice(@PathVariable Long id) {
    officeService.disableOffice(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enableOffice(@PathVariable Long id) {
    officeService.enableOffice(id);
    return ResponseEntity.noContent().build();
  }
}