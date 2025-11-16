package com.hospital.receipt_service.dto;

import com.hospital.receipt_service.model.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptUpdateDTO {
  
  @NotNull(message = "El método de pago es obligatorio")
  private PaymentMethod paymentMethod;

  @Size(max = 20, message = "El RUC no puede exceder los 20 caracteres")
  private String ruc;

  @Size(max = 255, message = "El nombre de la empresa no puede exceder los 255 caracteres")
  private String companyName;
}
