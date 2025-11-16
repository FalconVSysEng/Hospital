package com.hospital.receipt_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.receipt_service.dto.ReceiptRequestDTO;
import com.hospital.receipt_service.dto.ReceiptResponseDTO;
// import com.hospital.receipt_service.dto.ReceiptUpdateDTO;
import com.hospital.receipt_service.model.Status;
import com.hospital.receipt_service.service.ReceiptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

  private final ReceiptService receiptService;

  @PostMapping
  public ResponseEntity<ReceiptResponseDTO> createReceipt(@Valid @RequestBody ReceiptRequestDTO dto) {
    ReceiptResponseDTO receipt = receiptService.createReceipt(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
  }

  @GetMapping
  public ResponseEntity<List<ReceiptResponseDTO>> getAllReceipts() {
    List<ReceiptResponseDTO> receipts = receiptService.getAllReceipts();
    return ResponseEntity.ok(receipts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReceiptResponseDTO> getReceiptById(@PathVariable Long id) {
    ReceiptResponseDTO receipt = receiptService.getReceiptById(id);
    return ResponseEntity.ok(receipt);
  }

  @GetMapping("/appointment/{appointmentId}")
  public ResponseEntity<List<ReceiptResponseDTO>> getReceiptsByAppointmentId(
      @PathVariable Long appointmentId) {
    List<ReceiptResponseDTO> receipts = receiptService.getReceiptsByAppointmentId(appointmentId);
    return ResponseEntity.ok(receipts);
  }

  @GetMapping("/employee/{employeeDni}")
  public ResponseEntity<List<ReceiptResponseDTO>> getReceiptsByEmployeeDni(@PathVariable String employeeDni) {
    List<ReceiptResponseDTO> receipts = receiptService.getReceiptsByEmployeeDni(employeeDni);
    return ResponseEntity.ok(receipts);
  }

  @GetMapping("/created-at-range/{start}/{end}")
  public ResponseEntity<List<ReceiptResponseDTO>> getReceiptsByCreatedAtRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    List<ReceiptResponseDTO> receipts = receiptService.getReceiptsByCreatedAtRange(start, end);
    return ResponseEntity.ok(receipts);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateReceiptStatus(
      @PathVariable Long id,
      @RequestParam Status status) {
    receiptService.updateReceiptStatus(id, status);
    return ResponseEntity.noContent().build();
  }

  // @PutMapping("/{id}")
  // public ResponseEntity<ReceiptResponseDTO> updateReceipt(
  //     @PathVariable Long id,
  //     @Valid @RequestBody ReceiptUpdateDTO dto) {
  //   ReceiptResponseDTO receipt = receiptService.updateReceipt(id, dto);
  //   return ResponseEntity.ok(receipt);
  // }

}
