package com.hospital.prescription_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.prescription_service.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{
    List<Prescription> findByAttentionId(Long attentionId);
}
