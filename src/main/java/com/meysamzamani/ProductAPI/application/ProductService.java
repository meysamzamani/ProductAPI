package com.meysamzamani.ProductAPI.application;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import com.meysamzamani.ProductAPI.presentation.dto.ProductUpdateDTO;
import com.meysamzamani.ProductAPI.presentation.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return;
        }
        throw new NotFoundException("Product with ID " + productId + " not found.");
    }

    public Product updateProduct(Long productId, ProductUpdateDTO productUpdate) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            Product existProduct = product.get();

            if (productUpdate.getName() != null) {
                existProduct.setName(productUpdate.getName());
            }

            if (productUpdate.getPrice() > 0) {
                existProduct.setPrice(productUpdate.getPrice());
            }

            if (productUpdate.isOnSale() != existProduct.isOnSale()) {
                existProduct.setOnSale(productUpdate.isOnSale());
            }

            if (productUpdate.getBrand() != null) {
                existProduct.setBrand(productUpdate.getBrand());
            }

            return productRepository.save(existProduct);
        }

        throw new NotFoundException("Product with ID " + productId + " not found.");
    }

    public Product getProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        throw new NotFoundException("Product with ID " + productId + " not found.");
    }
}