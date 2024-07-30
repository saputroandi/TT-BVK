package com.bvk.orderservice.services;

import com.bvk.orderservice.clients.ProductClient;
import com.bvk.orderservice.dto.OrderRequest;
import com.bvk.orderservice.dto.PurchaseResponse;
import com.bvk.orderservice.entities.Order;
import com.bvk.orderservice.entities.OrderProduct;
import com.bvk.orderservice.helper.OrderProductMapper;
import com.bvk.orderservice.repositories.OrderProductRepository;
import com.bvk.orderservice.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Transactional
    public Long createOrder(OrderRequest request){
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<PurchaseResponse> purchaseResponses = productClient.purchaseProducts(request.getProducts());

        for (PurchaseResponse purchase : purchaseResponses) {
            totalAmount = totalAmount.add(purchase.getTotalAmount());
        }

        Order order = Order.builder()
                .reference(request.getReference())
                .paid(false)
                .totalAmount(totalAmount)
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderProduct> orderProducts = OrderProductMapper.fromPurchaseResponseList(purchaseResponses, order);

        orderProductRepository.saveAll(orderProducts);

        return savedOrder.getId();
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order not found"));
    }
}
