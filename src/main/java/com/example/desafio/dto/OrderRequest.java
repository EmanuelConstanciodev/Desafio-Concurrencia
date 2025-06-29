package com.example.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "customerId no puede estar vacío")
    private String customerId;

    @NotNull(message = "orderAmount no puede ser nulo")
    @DecimalMin(value = "0.01", message = "orderAmount debe ser mayor que 0")
    private Double orderAmount;

    @NotEmpty(message = "Debe haber al menos un item")
    private List<@NotBlank String> orderItems;

    @NotBlank(message = "orderId no puede estar vacío")
    private String orderId;
}
