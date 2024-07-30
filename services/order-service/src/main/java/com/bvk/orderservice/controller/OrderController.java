package com.bvk.orderservice.controller;

import com.bvk.orderservice.dto.OrderRequest;
import com.bvk.orderservice.entities.Order;
import com.bvk.orderservice.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createOrder(
            @RequestBody @Valid OrderRequest request
    ) {

        Map<String, Long> response = new HashMap<>();

        response.put("orderId", orderService.createOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(
            @PathVariable("id") Long orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(orderId));
    }
}
