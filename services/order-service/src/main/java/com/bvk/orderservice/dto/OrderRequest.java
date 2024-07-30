package com.bvk.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@JsonInclude( JsonInclude.Include.NON_EMPTY)
@Data
public class OrderRequest {

    @NotBlank
    private String reference;

    @NotEmpty
    private List<PurchaseRequest> products;
}
