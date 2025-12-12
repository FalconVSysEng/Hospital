package com.hospital.medicine_cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.medicine_cart.dto.MedicineDTO;

@FeignClient(name = "medicine-service")
public interface MedicineServiceClient {
    @GetMapping("/medicine/{id}")
    MedicineDTO findById(@PathVariable("id") Long MedicineId);
}
