package com.bvk.orderservice.helper;

import com.bvk.orderservice.dto.PurchaseResponse;
import com.bvk.orderservice.entities.Order;
import com.bvk.orderservice.entities.OrderProduct;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProductMapper {

    public static List<OrderProduct> fromPurchaseResponseList(List<PurchaseResponse> purchaseResponses, Order order) {
        return purchaseResponses.stream()
                .map(purchaseResponse -> OrderProduct.builder()
                        .productId(purchaseResponse.getId())
                        .quantity(purchaseResponse.getQuantity())
                        .order(order)
                        .build())
                .collect(Collectors.toList());
    }
}
