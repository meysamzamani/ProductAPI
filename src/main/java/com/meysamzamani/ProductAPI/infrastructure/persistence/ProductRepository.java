package com.meysamzamani.ProductAPI.infrastructure.persistence;

import com.meysamzamani.ProductAPI.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
