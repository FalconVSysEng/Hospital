package com.hospital.schedule_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.schedule_service.dto.EmployeeDTO;

@FeignClient(name = "employee-service")
public interface EmployeeServiceClient {

  @GetMapping("/employees/dni/{dni}")
  EmployeeDTO getEmployeeByDni(@PathVariable("dni") String dni);
}
