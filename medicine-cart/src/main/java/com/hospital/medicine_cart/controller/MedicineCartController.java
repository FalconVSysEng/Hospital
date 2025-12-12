package com.hospital.medicine_cart.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.medicine_cart.dto.MedicineCartItemRequestDTO;
import com.hospital.medicine_cart.dto.MedicineCartItemResponseDTO;
import com.hospital.medicine_cart.service.MedicineCartService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/medicine-cart")
public class MedicineCartController {
    @Autowired
    private MedicineCartService service;

    @PostMapping("/agregar")
    public ResponseEntity<MedicineCartItemResponseDTO> agregar(@Valid @RequestBody MedicineCartItemRequestDTO dto) {
        MedicineCartItemResponseDTO item = service.agregar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/quitar/{id}")
    public void quitar(@PathVariable Long id) {
        service.quitar(id);
    }

    @GetMapping("/listar")
    public List<MedicineCartItemResponseDTO> listar() {
        return service.listar();
    }

    @DeleteMapping("/nuevo")
    public void nuevo() {
        service.nuevo();
    }
}
