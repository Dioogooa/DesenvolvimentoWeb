package com.petshop.api.service;

import com.petshop.api.dto.request.ProductRequest;
import com.petshop.api.dto.response.ProductResponse;
import com.petshop.api.exception.ResourceNotFoundException;
import com.petshop.api.model.Category;
import com.petshop.api.model.Product;
import com.petshop.api.repository.CategoryRepository;
import com.petshop.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponse> findAll() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> search(String name) {
        return productRepository.searchByName(name)
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Produto", id));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.getCategoryId()));
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .category(category)
                .active(true)
                .build();

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.getCategoryId()));

                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setStock(request.getStock());
                product.setImageUrl(request.getImageUrl());
                product.setCategory(category);


        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));

        product.setActive(false);
        productRepository.save(product);
    }
}
