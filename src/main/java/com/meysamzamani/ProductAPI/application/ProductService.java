package com.meysamzamani.ProductAPI.application;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.domain.ProductSpecifications;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import com.meysamzamani.ProductAPI.presentation.dto.GroupedProductDTO;
import com.meysamzamani.ProductAPI.presentation.dto.ProductUpdateDTO;
import com.meysamzamani.ProductAPI.presentation.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Map<String,List<GroupedProductDTO>> searchProducts(String name, Double minPrice, Double maxPrice, String brand, Boolean onSale) {

        Specification<Product> specification = Specification.where(null);
        if (name != null) {
            specification = specification.and(ProductSpecifications.nameContains(name));
        }
        if (minPrice != null) {
            specification = specification.and(ProductSpecifications.minPrice(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductSpecifications.maxPrice(maxPrice));
        }
        if (brand != null) {
            specification = specification.and(ProductSpecifications.brandEquals(brand));
        }
        if (onSale != null) {
            specification = specification.and(ProductSpecifications.onSaleEquals(onSale));
        }

        Sort sort = Sort.by(
                Sort.Order.asc("brand").ignoreCase(),
                Sort.Order.asc("price")
        );

        return getGroupedProducts(productRepository.findAll(specification, sort));
    }

    private Map<String, List<GroupedProductDTO>> getGroupedProducts(List<Product> products) {
        Map<String, List<GroupedProductDTO>> groupedProducts = new LinkedCaseInsensitiveMap<>();
        products.forEach(product -> {
            String brand = product.getBrand();
            GroupedProductDTO productDTO = new GroupedProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.isOnSale() ? "ON SALE" : null
            );
            groupedProducts.computeIfAbsent(brand, k -> new ArrayList<>()).add(productDTO);
        });
        return groupedProducts;
    }

}