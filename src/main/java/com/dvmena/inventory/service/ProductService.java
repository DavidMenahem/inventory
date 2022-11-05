package com.dvmena.inventory.service;

import com.dvmena.inventory.model.Product;
import com.dvmena.inventory.model.SubCategory;
import com.dvmena.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void add(Product product){
        productRepository.save(product);
    }

    public List<Product> findBySubCategoryId(long id){
        return productRepository.findBySubCategoryId(id);
    }

    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }
}