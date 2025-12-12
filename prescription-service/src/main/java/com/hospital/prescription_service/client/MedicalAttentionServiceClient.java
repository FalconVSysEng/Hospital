package com.hospital.prescription_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.prescription_service.dto.MedicalAttentionDTO;

@FeignClient(name = "medical-attention-service")
public interface MedicalAttentionServiceClient {
    @GetMapping("/medical-attention/{id}/simple")
    MedicalAttentionDTO getAttentionByIdSimple(@PathVariable Long id);
}
