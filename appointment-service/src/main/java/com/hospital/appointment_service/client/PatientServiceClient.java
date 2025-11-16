package com.hospital.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.appointment_service.dto.PatientDTO;

@FeignClient(name = "patient-service")
public interface PatientServiceClient {

  @GetMapping("/patients/id/{id}")
  PatientDTO getPatientById(@PathVariable Long id);

}
