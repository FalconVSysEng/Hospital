package com.hospital.auth_service.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.auth_service.client.EmployeeServiceClient;
import com.hospital.auth_service.dto.AuthRequestDTO;
import com.hospital.auth_service.dto.AuthResponseDTO;
import com.hospital.auth_service.dto.EmployeeDTO;
import com.hospital.auth_service.model.Employee;
import com.hospital.auth_service.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final EmployeeRepository employeeRepository;
  private final JwtService jwtService;
  private final EmployeeServiceClient employeeServiceClient;
  private final ModelMapper modelMapper;

  public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequestDTO.getDni(), authRequestDTO.getPassword()));

    Employee employee = employeeRepository.findByDni(authRequestDTO.getDni())
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new DisabledException("La cuenta está desactivada");
    }

    EmployeeDTO employeeDTO = employeeServiceClient.getEmployeeByDni(authRequestDTO.getDni());
    employeeDTO.setRole(employee.getRole());

    String accessToken = jwtService.generateAccessToken(employee);
    String refreshToken = jwtService.generateRefreshToken(employee);

    return new AuthResponseDTO(accessToken, refreshToken, employeeDTO);
  }

  public void registerUser(Employee newEmployee) {
    if (employeeRepository.findByDni(newEmployee.getDni()).isPresent()) {
      throw new IllegalArgumentException("El DNI ya está registrado en el servicio de autenticación.");
    }
    newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
    newEmployee.setIsEnabled(true);
    employeeRepository.save(newEmployee);
  }

  public EmployeeDTO getUserByDni(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

    return modelMapper.map(employee, EmployeeDTO.class);
  }

  public void disableUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
    employee.setIsEnabled(false);
    employeeRepository.save(employee);
  }

  public void enableUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
    employee.setIsEnabled(true);
    employeeRepository.save(employee);
  }

  public void deleteUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado en auth-service"));
    employee.setIsEnabled(false);
    employeeRepository.delete(employee);
  }
}