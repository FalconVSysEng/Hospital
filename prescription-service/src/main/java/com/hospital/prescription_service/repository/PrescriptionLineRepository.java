package com.hospital.prescription_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.prescription_service.model.PrescriptionLine;

public interface PrescriptionLineRepository extends JpaRepository<PrescriptionLine, Long> {
    List<PrescriptionLine> findByPrescriptionId(Long prescriptionId);
}
