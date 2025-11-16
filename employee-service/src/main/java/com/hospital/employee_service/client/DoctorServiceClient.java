package com.hospital.employee_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hospital.employee_service.dto.DoctorRequestDTO;

@FeignClient(name = "doctor-service")
public interface DoctorServiceClient {

  @PostMapping("/doctors")
  void createDoctor(@RequestBody DoctorRequestDTO requestDTO);
}