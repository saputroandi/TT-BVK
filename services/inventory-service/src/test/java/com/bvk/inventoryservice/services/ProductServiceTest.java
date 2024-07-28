package com.bvk.inventoryservice.services;

import com.bvk.inventoryservice.dto.ProductRequest;
import com.bvk.inventoryservice.dto.ProductResponse;
import com.bvk.inventoryservice.eitities.Product;
import com.bvk.inventoryservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getListProduct() {
        productService.getListProduct();

        verify(productRepository).findAll();
    }

    @Test
    void createProduct() {
        ProductRequest request = createProductRequest();
        Product savedProduct = createProduct(1L);

        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(savedProduct.getId(), response.getId());
        assertEquals(savedProduct.getName(), response.getName());
        assertEquals(savedProduct.getPrice(), response.getPrice());
        assertEquals(savedProduct.getQuantity(), response.getQuantity());

        verify(productRepository).save(productArgumentCaptor.capture());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        Long id = 1L;
        ProductRequest request = createProductRequest();
        Product product = createProduct(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.updateProduct(id, request);

        assertNotNull(response);
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getPrice(), response.getPrice());
        assertEquals(product.getQuantity(), response.getQuantity());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateProduct_EntityNotFound() {
        Long id = 2L;
        ProductRequest request = createProductRequest();

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            productService.updateProduct(id, request);
        });

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void removeProduct() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        productService.removeProduct(id);

        verify(productRepository, times(1)).existsById(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void removeProduct_EntityNotFound() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            productService.removeProduct(productId);
        });

        assertTrue(thrown.getMessage().contains("not found"));

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(0)).deleteById(productId);
    }

    private ProductRequest createProductRequest(){
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100.0));
        request.setQuantity(10L);

        return request;
    }

    private Product createProduct(Long id){
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setQuantity(10L);

        return product;
    }
}