package com.hospital.schedule_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.schedule_service.dto.OfficeDTO;

@FeignClient(name = "office-service")
public interface OfficeServiceClient {

  @GetMapping("/offices/{id}")
  OfficeDTO getOfficeById(@PathVariable("id") Long id);
}
