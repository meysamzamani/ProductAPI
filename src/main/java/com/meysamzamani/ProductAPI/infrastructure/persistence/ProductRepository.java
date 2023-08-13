package com.meysamzamani.ProductAPI.infrastructure.persistence;

import com.meysamzamani.ProductAPI.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(Specification<Product> specification, Sort sort);

}
