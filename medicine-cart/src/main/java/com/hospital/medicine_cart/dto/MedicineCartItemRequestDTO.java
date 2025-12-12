package com.hospital.medicine_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineCartItemRequestDTO {
    @NotNull(message = "El ID de la medicina es obligatorio")
    private Long MedicineId;
    @NotNull(message = "La dosis es obligatoria")
    private String dose;
    @NotNull(message = "La frecuencia es obligatoria")
    private String frequency;
    @NotNull(message = "La duracion es obligatoria")
    private String duration;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mínimo 1")
    private Integer quantity;
}