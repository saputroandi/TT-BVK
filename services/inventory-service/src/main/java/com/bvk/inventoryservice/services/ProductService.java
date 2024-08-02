package com.bvk.inventoryservice.services;

import com.bvk.inventoryservice.dto.ProductRequest;
import com.bvk.inventoryservice.dto.ProductResponse;
import com.bvk.inventoryservice.dto.PurchasedProductRequest;
import com.bvk.inventoryservice.eitities.Product;
import com.bvk.inventoryservice.exceptions.ProductPurchaseException;
import com.bvk.inventoryservice.helper.ProductMapper;
import com.bvk.inventoryservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> getListProduct() {

        return productRepository.findAll().stream().map(ProductMapper::toProductResponse).toList();
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = ProductMapper.toProduct(request);
        return ProductMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product not found"));

        return ProductMapper.toProductResponse(productRepository.save(product));
    }

    public void removeProduct(Long id) {

        if ( !productRepository.existsById(id) ) {
            throw new EntityNotFoundException("product not found");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public List<ProductResponse> purchaseProduct(List<PurchasedProductRequest> request) {

        Map<Long, PurchasedProductRequest> requestMap = request.stream()
                .collect(Collectors.toMap(PurchasedProductRequest::getProductId, purchasedRequest -> purchasedRequest));

        List<Long> listProductId = request
                .stream()
                .map(PurchasedProductRequest::getProductId)
                .toList();

        List<Product> storedProducts = productRepository.findAllById(listProductId);

        if ( listProductId.size() != storedProducts.size() ) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var purchasedProducts = new ArrayList<ProductResponse>();

        for ( Product product : storedProducts ) {
            PurchasedProductRequest purchasedProduct = requestMap.get(product.getId());

            if ( product.getQuantity() < purchasedProduct.getQuantity() ) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID: " + purchasedProduct.getProductId());
            }

            Long newAvailableQuantity = product.getQuantity() - purchasedProduct.getQuantity();
            product.setQuantity(newAvailableQuantity);
            purchasedProducts.add(ProductMapper.toProductResponse(product, purchasedProduct.getQuantity()));
        }

        productRepository.saveAll(storedProducts);

        return purchasedProducts;
    }
}
