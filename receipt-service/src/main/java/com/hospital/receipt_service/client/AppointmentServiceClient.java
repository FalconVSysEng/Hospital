package com.hospital.receipt_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.receipt_service.dto.MedicalAppointmentDTO;

@FeignClient(name = "appointment-service")
public interface AppointmentServiceClient {

  @GetMapping("/appointments/{id}")
  MedicalAppointmentDTO getAppointmentById(@PathVariable Long id);

}
