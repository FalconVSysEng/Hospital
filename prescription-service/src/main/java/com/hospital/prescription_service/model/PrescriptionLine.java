package com.hospital.prescription_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "analysis_sheet_line")
@NoArgsConstructor

public class PrescriptionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long PrescriptionId;
    private Long medicineId;
    private String dose;
    private String frequency;
    private String duration;
    private Integer quantity;
}
