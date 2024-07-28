package com.bvk.inventoryservice.controllers;

import com.bvk.inventoryservice.dto.ProductRequest;
import com.bvk.inventoryservice.dto.ProductResponse;
import com.bvk.inventoryservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getListProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getListProduct());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,@Valid @RequestBody ProductRequest request ){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, request));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id){
        productService.removeProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
