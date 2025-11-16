package com.hospital.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.appointment_service.dto.OfficeDTO;

@FeignClient(name = "office-service")
public interface OfficeServiceClient {

  @GetMapping("/offices/{id}")
  OfficeDTO getOfficeById(@PathVariable Long id);

}
