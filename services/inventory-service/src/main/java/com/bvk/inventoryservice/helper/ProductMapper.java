package com.bvk.inventoryservice.helper;

import com.bvk.inventoryservice.eitities.Product;
import com.bvk.inventoryservice.dto.ProductRequest;
import com.bvk.inventoryservice.dto.ProductResponse;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public static ProductResponse toProductResponse(Product product, Long quantity){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public static Product toProduct(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
