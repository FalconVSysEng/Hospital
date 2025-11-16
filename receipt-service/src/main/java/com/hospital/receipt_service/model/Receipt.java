package com.hospital.receipt_service.model;

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
@AllArgsConstructor
@Builder
@Table(name = "receipt")
@NoArgsConstructor
public class Receipt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String employeeDni;

  @Column(nullable = false)
  private Long appointmentId;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private BigDecimal totalAmount;

  @Column(nullable = false)
  private PaymentMethod paymentMethod;

  private String ruc;
  private String companyName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;
}
