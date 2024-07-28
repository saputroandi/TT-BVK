package com.bvk.inventoryservice.services;

import com.bvk.inventoryservice.eitities.Product;
import com.bvk.inventoryservice.helper.ProductMapper;
import com.bvk.inventoryservice.dto.ProductRequest;
import com.bvk.inventoryservice.dto.ProductResponse;
import com.bvk.inventoryservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> getListProduct(){
        return productRepository.findAll().stream().map(ProductMapper::toProductResponse).toList();
    }

    public ProductResponse createProduct(ProductRequest request){

        Product product = ProductMapper.toProduct(request);
        return ProductMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest request){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product not found"));

        return ProductMapper.toProductResponse(productRepository.save(product));
    }

    public void removeProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new EntityNotFoundException("product not found");
        }
        productRepository.deleteById(id);
    }
}
