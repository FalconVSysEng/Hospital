package com.hospital.medical_history_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.medical_history_service.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
  Optional<MedicalHistory> findByPatientId(Long patientId);

  List<MedicalHistory> findByEmployeeDni(String employeeDni);

  List<MedicalHistory> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

  List<MedicalHistory> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
}
