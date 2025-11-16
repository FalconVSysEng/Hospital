package com.hospital.receipt_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.receipt_service.model.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

  List<Receipt> findByAppointmentId(Long appointmentId);

  List<Receipt> findByEmployeeDni(String employeeDni);

  List<Receipt> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
