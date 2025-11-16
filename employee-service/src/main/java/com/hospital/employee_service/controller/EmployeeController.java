package com.hospital.employee_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.employee_service.dto.EmployeeRequestDTO;
import com.hospital.employee_service.dto.EmployeeDTO;
import com.hospital.employee_service.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
    EmployeeDTO employeeDTO = employeeService.createEmployee(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    List<EmployeeDTO> employees = employeeService.getAllEmployees();
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/dni/{dni}")
  public ResponseEntity<EmployeeDTO> getEmployeeByDni(@PathVariable String dni) {
    EmployeeDTO employee = employeeService.getEmployeeByDni(dni);
    return ResponseEntity.ok(employee);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
    EmployeeDTO employee = employeeService.getEmployeeById(id);
    return ResponseEntity.ok(employee);
  }
}