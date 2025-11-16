package com.hospital.doctor_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.doctor_service.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
  Optional<Doctor> findByDni(String dni);

  List<Doctor> findBySpecialtyIdsContaining(Long specialtyId);
}
