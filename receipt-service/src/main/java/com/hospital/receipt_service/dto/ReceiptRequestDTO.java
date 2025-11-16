package com.hospital.receipt_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.hospital.receipt_service.model.PaymentMethod;
import com.hospital.receipt_service.model.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequestDTO {
  @NotBlank(message = "El DNI del empleado no puede estar vacío")
  @Size(min = 8, max = 15, message = "El DNI del empleado debe tener entre 8 y 15 caracteres")
  private String employeeDni;

  @NotNull(message = "El ID de la cita médica es obligatorio")
  private Long appointmentId;

  @NotNull(message = "El método de pago es obligatorio")
  private PaymentMethod paymentMethod;

  @Size(max = 20, message = "El RUC no puede exceder los 20 caracteres")
  private String ruc;

  @Size(max = 255, message = "El nombre de la empresa no puede exceder los 255 caracteres")
  private String companyName;

  @Builder.Default
  private Status status = Status.PENDIENTE;
}