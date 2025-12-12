package com.hospital.prescription_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.prescription_service.dto.MedicineCartItemDTO;

@FeignClient(name = "analysis-cart")
public interface MedicineCartServiceCliente {
    @GetMapping("/analysis-cart/listar")
    List<MedicineCartItemDTO> listar();

    @DeleteMapping("/analysis-cart/nuevo")
    void limpiarCarrito();
}
