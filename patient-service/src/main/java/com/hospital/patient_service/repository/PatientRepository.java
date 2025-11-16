package com.hospital.patient_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.patient_service.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByDni(String dni);
}
