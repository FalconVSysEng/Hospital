package com.hospital.office_service.client;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "specialty-service")
public interface SpecialtyServiceClient {
  @PostMapping("/specialties/exists")
  Set<Long> getExistingSpecialtyIds(@RequestBody Set<Long> specialtyIds);
}