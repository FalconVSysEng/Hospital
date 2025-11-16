package com.hospital.schedule_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.schedule_service.dto.SpecialtyDTO;

@FeignClient(name = "specialty-service")
public interface SpecialtyServiceClient {

  @GetMapping("/specialties/{id}")
  SpecialtyDTO getSpecialtyById(@PathVariable("id") Long id);
}
