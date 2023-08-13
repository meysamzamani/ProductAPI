package com.meysamzamani.ProductAPI.infrastructure.persistence;

import com.meysamzamani.ProductAPI.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepositoryUnderTest;

    @BeforeEach
    void setUp() {
        productRepositoryUnderTest.deleteAll();
    }

    @AfterEach
    void tearDown() {
        productRepositoryUnderTest.deleteAll();
    }

    @Test
    void givenProduct_whenSaveIntoDatabase_thenShouldFindById() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product savedProduct = productRepositoryUnderTest.save(product);

        Optional<Product> findedProduct = productRepositoryUnderTest.findById(savedProduct.getId());

        assertAll(
                () -> assertEquals(1, productRepositoryUnderTest.findAll().size()),
                () -> assertTrue(findedProduct.isPresent()),
                () -> assertEquals(savedProduct.getName(), findedProduct.get().getName()),
                () -> assertEquals(savedProduct.getId(), findedProduct.get().getId())
        );
    }

    @Test
    void givenProducts_whenSaved_thenShouldReturnTwoProducts() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product product2 = new Product("iPhone13Pro", 1000.00, "apple", false);
        productRepositoryUnderTest.save(product);
        productRepositoryUnderTest.save(product2);

        List<Product> savedProducts = productRepositoryUnderTest.findAll();

        assertAll(
                () -> assertNotNull(savedProducts),
                () -> assertEquals(2, savedProducts.size())
        );
    }

    @Test
    void givenProduct_whenDeleteById_thenNotExist() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product savedProduct = productRepositoryUnderTest.save(product);
        Long id = savedProduct.getId();
        assertEquals(1, productRepositoryUnderTest.findAll().size());

        productRepositoryUnderTest.deleteById(id);
        assertEquals(0, productRepositoryUnderTest.findAll().size());

        Optional<Product> productById = productRepositoryUnderTest.findById(id);
        assertFalse(productById.isPresent());
    }
}