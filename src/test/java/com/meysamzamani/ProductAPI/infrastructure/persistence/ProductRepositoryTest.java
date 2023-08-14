package com.meysamzamani.ProductAPI.infrastructure.persistence;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.domain.ProductSpecifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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

    @Test
    void givenProducts_whenFindAllByCriteria_thenShouldEqualAndSorted() {
        Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
        Product pixel7a = new Product("Pixel7A", 800.90, "Google", true);
        Product slflex = new Product("SoundLink Flex", 164.95, "bose", true);
        Product Pixel6a = new Product("Pixel61", 345.00, "gOogle", true);
        Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "apple", false);
        Product appleWatchUltra = new Product("AppleWatchUltra", 999.00, "aPple", true);
        productRepositoryUnderTest.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));

        assertEquals(7, productRepositoryUnderTest.findAll().size());

        Specification<Product> specification = Specification.where(null);
        specification = specification.and(ProductSpecifications.brandEquals("Apple"));
        specification = specification.and(ProductSpecifications.maxPrice(1000.00));

        Sort sort = Sort.by(
                Sort.Order.asc("brand").ignoreCase(),
                Sort.Order.asc("price")
        );

        List<Product> actualProducts = productRepositoryUnderTest.findAll(specification, sort);

        assertAll(
                () -> assertNotNull(actualProducts),
                () -> assertEquals(2, actualProducts.size()),
                () -> assertEquals("iPhoneSE2", actualProducts.get(0).getName()),
                () -> assertEquals("AppleWatchUltra", actualProducts.get(1).getName())
        );
    }

    @Test
    void givenProducts_whenFindAllByCriteria_thenShouldEmpty() {
        Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
        Product pixel7a = new Product("Pixel7A", 800.90, "Google", true);
        Product slflex = new Product("SoundLink Flex", 164.95, "bose", true);
        Product Pixel6a = new Product("Pixel61", 345.00, "gOogle", true);
        Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "apple", false);
        Product appleWatchUltra = new Product("AppleWatchUltra", 999.00, "aPple", true);
        productRepositoryUnderTest.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));

        assertEquals(7, productRepositoryUnderTest.findAll().size());

        Specification<Product> specification = Specification.where(null);
        specification = specification.and(ProductSpecifications.brandEquals("xiaomi"));
        specification = specification.and(ProductSpecifications.maxPrice(1000.00));

        Sort sort = Sort.by(
                Sort.Order.asc("brand").ignoreCase(),
                Sort.Order.asc("price")
        );

        List<Product> actualProducts = productRepositoryUnderTest.findAll(specification, sort);

        assertAll(
                () -> assertNotNull(actualProducts),
                () -> assertEquals(0, actualProducts.size())
        );
    }

    @Test
    void givenProducts_whenFindAllByCriteria_thenShouldSortedByBrand() {
        Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "Apple", true);
        Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
        Product pixel7a = new Product("Pixel7A", 800.90, "Google", true);
        Product slflex = new Product("SoundLink Flex", 164.95, "bose", true);
        Product Pixel6a = new Product("Pixel61", 345.00, "gOogle", true);
        Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "apple", false);
        Product appleWatchUltra = new Product("AppleWatchUltra", 999.00, "aPple", true);
        productRepositoryUnderTest.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));

        assertEquals(7, productRepositoryUnderTest.findAll().size());

        Specification<Product> specification = Specification.where(null);
        specification = specification.and(ProductSpecifications.maxPrice(700.00));

        Sort sort = Sort.by(
                Sort.Order.asc("brand").ignoreCase(),
                Sort.Order.asc("price")
        );

        List<Product> actualProducts = productRepositoryUnderTest.findAll(specification, sort);

        assertAll(
                () -> assertNotNull(actualProducts),
                () -> assertEquals(3, actualProducts.size()),
                () -> assertEquals("Bose", actualProducts.get(0).getBrand()),
                () -> assertEquals("bose", actualProducts.get(1).getBrand()),
                () -> assertEquals("gOogle", actualProducts.get(2).getBrand())
        );
    }

}