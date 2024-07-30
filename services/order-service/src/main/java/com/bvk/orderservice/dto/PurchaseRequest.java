package com.bvk.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class PurchaseRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Long quantity;
}
