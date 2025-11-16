package com.hospital.specialty_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.specialty_service.model.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
  Optional<Specialty> findByName(String name);
}