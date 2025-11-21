package com.hospital.auth_service.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.auth_service.client.EmployeeServiceClient;
import com.hospital.auth_service.dto.AuthRequestDTO;
import com.hospital.auth_service.dto.AuthResponseDTO;
import com.hospital.auth_service.dto.EmployeeDTO;
import com.hospital.auth_service.exception.CustomException;
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
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequestDTO.getDni(), authRequestDTO.getPassword()));
    } catch (Exception e) {
      throw new CustomException("DNI o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
    }

    Employee employee = employeeRepository.findByDni(authRequestDTO.getDni())
        .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));

    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new CustomException("La cuenta está desactivada", HttpStatus.FORBIDDEN);
    }

    EmployeeDTO employeeDTO = employeeServiceClient.getEmployeeByDni(authRequestDTO.getDni());
    employeeDTO.setRole(employee.getRole());

    String accessToken = jwtService.generateAccessToken(employee);
    String refreshToken = jwtService.generateRefreshToken(employee);

    return new AuthResponseDTO(accessToken, refreshToken, employeeDTO);
  }

  public void registerUser(Employee newEmployee) {
    if (employeeRepository.findByDni(newEmployee.getDni()).isPresent()) {
      throw new CustomException("El DNI ya está registrado en el servicio de autenticación", HttpStatus.CONFLICT);
    }
    newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
    newEmployee.setIsEnabled(true);
    employeeRepository.save(newEmployee);
  }

  public EmployeeDTO getUserByDni(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Empleado no encontrado", HttpStatus.NOT_FOUND));

    return modelMapper.map(employee, EmployeeDTO.class);
  }

  public void disableUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Empleado no encontrado", HttpStatus.NOT_FOUND));
    employee.setIsEnabled(false);
    employeeRepository.save(employee);
  }

  public void enableUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Empleado no encontrado", HttpStatus.NOT_FOUND));
    employee.setIsEnabled(true);
    employeeRepository.save(employee);
  }

  public void deleteUser(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Empleado no encontrado en auth-service", HttpStatus.NOT_FOUND));
    employee.setIsEnabled(false);
    employeeRepository.delete(employee);
  }
}