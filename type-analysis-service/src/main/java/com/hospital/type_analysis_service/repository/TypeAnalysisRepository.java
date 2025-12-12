package com.hospital.type_analysis_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.type_analysis_service.model.TypeAnalysis;

public interface TypeAnalysisRepository extends JpaRepository<TypeAnalysis, Long> {
    Optional<TypeAnalysis> findByNameIgnoreCase(String name); 
}
