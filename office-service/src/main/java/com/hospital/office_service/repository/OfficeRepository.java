package com.hospital.office_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.office_service.model.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {

}
