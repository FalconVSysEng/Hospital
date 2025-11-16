package com.hospital.appointment_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long scheduleId;

  @Column(nullable = false)
  private String employeeDni;

  @Column(nullable = false)
  private Long patientId;

  @Column(nullable = false)
  private Long officeId;

  @Column(nullable = false)
  private LocalDateTime solicitationDateTime;

  @Column(nullable = false)
  private BigDecimal finalCost;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;
}
