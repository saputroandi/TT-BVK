package com.bvk.inventoryservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasedProductRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Long quantity;
}
