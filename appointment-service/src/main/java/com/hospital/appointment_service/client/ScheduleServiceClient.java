package com.hospital.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.appointment_service.dto.ScheduleDTO;

@FeignClient(name = "schedule-service")
public interface ScheduleServiceClient {

  @GetMapping("/schedules/{id}")
  ScheduleDTO getScheduleById(@PathVariable Long id);

}
