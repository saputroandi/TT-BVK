package com.bvk.inventoryservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Long quantity;
}
