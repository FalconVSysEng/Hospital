package com.hospital.employee_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hospital.employee_service.dto.EmployeeRequestDTO;
import com.hospital.employee_service.model.Role;
import com.hospital.employee_service.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataInitializer {

  @Bean
  public CommandLineRunner initializeAdmin(EmployeeService employeeService) {
    return args -> {
      Thread.sleep(3000);

      int maxRetries = 5;
      int retryCount = 0;
      long waitTime = 2000; // 2 segundos inicial

      while (retryCount < maxRetries) {
        try {
          EmployeeRequestDTO adminDto = new EmployeeRequestDTO();
          adminDto.setName("Admin");
          adminDto.setLastname("System");
          adminDto.setDni("12345678");
          adminDto.setRole(Role.ADMIN);
          adminDto.setPassword("admin123");

          employeeService.createEmployee(adminDto);
          log.info("Usuario ADMIN creado exitosamente - DNI: 12345678, Password: admin123");
          return;
        } catch (IllegalArgumentException e) {
          log.info("Usuario ADMIN ya existe, omitiendo creación");
          return;
        } catch (Exception e) {
          retryCount++;
          if (retryCount >= maxRetries) {
            log.error("Error al crear usuario ADMIN después de {} intentos: {}", maxRetries, e.getMessage());
          } else {
            log.warn("Intento {} fallido al crear usuario ADMIN. Reintentando en {}ms...", retryCount, waitTime);
            Thread.sleep(waitTime);
            waitTime *= 2;
          }
        }
      }
    };
  }
}
