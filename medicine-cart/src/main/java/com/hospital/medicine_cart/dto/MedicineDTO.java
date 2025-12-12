package com.hospital.medicine_cart.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    @NotNull(message = "El ID de la medicina es obligatorio")
    private Long id;
    private String name;
    private String description;
    private String concentration;
    private String presentation;
    private Boolean status;
}
