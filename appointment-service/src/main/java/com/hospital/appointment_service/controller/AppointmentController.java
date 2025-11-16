package com.hospital.appointment_service.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.appointment_service.dto.AppointmentRequestDTO;
import com.hospital.appointment_service.dto.AppointmentResponseDTO;
import com.hospital.appointment_service.model.Status;
import com.hospital.appointment_service.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService appointmentService;

  @PostMapping
  public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
    AppointmentResponseDTO appointment = appointmentService.createAppointment(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
  }

  @GetMapping
  public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
    List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments();
    return ResponseEntity.ok(appointments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
    AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);
    return ResponseEntity.ok(appointment);
  }

  @GetMapping("/schedule/{scheduleId}")
  public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByScheduleId(@PathVariable Long scheduleId) {
    List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByScheduleId(scheduleId);
    return ResponseEntity.ok(appointments);
  }

  @GetMapping("/employee/{employeeDni}")
  public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByEmployeeDni(@PathVariable String employeeDni) {
    List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByEmployeeDni(employeeDni);
    return ResponseEntity.ok(appointments);
  }

  @GetMapping("/office/{officeId}")
  public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByOfficeId(@PathVariable Long officeId) {
    List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByOfficeId(officeId);
    return ResponseEntity.ok(appointments);
  }

  @GetMapping("/datetime-range/{start}/{end}")
  public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDateTimeRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDateTimeRange(start, end);
    return ResponseEntity.ok(appointments);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateAppointmentStatus(
      @PathVariable Long id,
      @RequestParam Status status) {
    appointmentService.updateAppointmentStatus(id, status);
    return ResponseEntity.noContent().build();
  }

}
