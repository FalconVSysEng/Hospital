package com.hospital.receipt_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hospital.receipt_service.model.PaymentMethod;
import com.hospital.receipt_service.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponseDTO {
  private Long id;
  private EmployeeDTO employee;
  private MedicalAppointmentDTO medicalAppointment;
  private PaymentMethod paymentMethod;
  private BigDecimal totalAmount;
  private String ruc;
  private String companyName;
  private LocalDateTime createdAt;
  private Status status;
}