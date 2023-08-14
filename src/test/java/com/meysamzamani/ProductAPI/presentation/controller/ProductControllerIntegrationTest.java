package com.meysamzamani.ProductAPI.presentation.controller;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import com.meysamzamani.ProductAPI.presentation.dto.ProductUpdateDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @LocalServerPort
    int port;

    String baseUrl = "http://localhost";

    static RestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/v1.0/products");
        productRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void givenProduct_whenGetCall_thenListProductAndEqualOne() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", false);
        productRepository.save(product);

        List<Product> products = restTemplate.getForObject(baseUrl,List.class);

        assertAll(
                () -> assertNotNull(products),
                () -> assertEquals(1, products.size()),
                () -> assertEquals(1, productRepository.findAll().size()));
    }

    @Test
    void givenProduct_whenGetCallById_thenProductAndEqualId() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", false);
        Product savedProduct = productRepository.save(product);

        Product response = restTemplate.getForObject(baseUrl+"/{id}", Product.class, savedProduct.getId());

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(savedProduct.getId(), response.getId()),
                () -> assertEquals("iPhone14Pro", response.getName()),
                () -> assertEquals(1100.00, response.getPrice()),
                () -> assertEquals("Apple", response.getBrand()),
                () -> assertFalse(response.isOnSale()),
                () -> assertEquals(1, productRepository.findAll().size()));
    }

    @Test
    void givenProduct_whenPostCall_thenShouldExistAndEqual() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", false);
        Product response = restTemplate.postForObject(baseUrl, product, Product.class);

        assertAll(() -> assertNotNull(response),
                () -> assertTrue(response.getId() > 0),
                () -> assertEquals("iPhone14Pro", response.getName()),
                () -> assertEquals(1100.00, response.getPrice()),
                () -> assertEquals("Apple", response.getBrand()),
                () -> assertFalse(response.isOnSale()),
                () -> assertEquals(1, productRepository.findAll().size()));
    }

    @Test
    void givenProduct_whenDeleteCallById_thenNotExist() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", false);
        Product savedProduct = productRepository.save(product);

        assertEquals(1, productRepository.findAll().size());
        assertTrue(productRepository.findById(savedProduct.getId()).isPresent());

        restTemplate.delete(baseUrl+"/{id}", savedProduct.getId());

        assertAll(
                () -> assertFalse(productRepository.findById(savedProduct.getId()).isPresent()),
                () -> assertEquals(0, productRepository.findAll().size()));
    }

    @Test
    void givenProduct_whenPutCallById_thenShouldMatch() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", false);
        Product savedProduct = productRepository.save(product);

        assertAll(
                () -> assertNotNull(savedProduct),
                () -> assertEquals("iPhone14Pro", savedProduct.getName()),
                () -> assertEquals(1100.00, savedProduct.getPrice()),
                () -> assertEquals("Apple", savedProduct.getBrand()),
                () -> assertFalse(savedProduct.isOnSale()),
                () -> assertEquals(1, productRepository.findAll().size()));

        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("iPhone13Pro");

        restTemplate.put(baseUrl+"/{id}", updateDTO, savedProduct.getId());

        Optional<Product> updatedProduct = productRepository.findById(savedProduct.getId());

        assertAll(() -> assertNotNull(updatedProduct),
                () -> assertTrue(updatedProduct.isPresent()),
                () -> assertEquals(savedProduct.getId(), updatedProduct.get().getId()),
                () -> assertEquals("iPhone13Pro", updatedProduct.get().getName()),
                () -> assertEquals(1100.00, updatedProduct.get().getPrice()),
                () -> assertEquals("Apple", updatedProduct.get().getBrand()),
                () -> assertFalse(updatedProduct.get().isOnSale()),
                () -> assertEquals(1, productRepository.findAll().size()));
    }

    @Test
    void givenProducts_whenSearch_thenShouldReturnGroupedProductsSortedByPrice() {
        Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "apple", true);
        Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
        Product pixel7a = new Product("Pixel7A", 800.90, "gOogle", true);
        Product slflex = new Product("SoundLink Flex", 164.95, "bose", true);
        Product Pixel6a = new Product("Pixel61", 345.00, "Google", true);
        Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "Apple", false);
        Product appleWatchUltra = new Product("AppleWatchUltra", 999.00, "aPple", true);
        productRepository.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));

        assertEquals(7, productRepository.findAll().size());

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(baseUrl+"/search", Map.class);
        Map<String, List<Map<String, Object>>> groupedProducts = responseEntity.getBody();

        assertNotNull(groupedProducts);
        assertFalse(groupedProducts.isEmpty());

        assertTrue(groupedProducts.containsKey("Apple"));
        assertTrue(groupedProducts.containsKey("Bose"));
        assertTrue(groupedProducts.containsKey("Google"));

        assertEquals(3, groupedProducts.get("Apple").size());
        assertEquals(2, groupedProducts.get("Bose").size());
        assertEquals(2, groupedProducts.get("Google").size());

        assertProductsSortedByPrice(groupedProducts.get("Apple"));
        assertProductsSortedByPrice(groupedProducts.get("Bose"));
        assertProductsSortedByPrice(groupedProducts.get("Google"));

    }

    @Test
    void givenProducts_whenSearchWithParameters_thenShouldReturnFilteredAndSortedProducts() {
        Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "apple", true);
        Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
        Product pixel7a = new Product("Pixel7A", 800.90, "gOogle", true);
        Product slflex = new Product("SoundLink Flex", 164.95, "bose", true);
        Product Pixel6a = new Product("Pixel61", 345.00, "Google", true);
        Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "Apple", false);
        Product appleWatchUltra = new Product("AppleWatchUltra", 1200.00, "aPple", true);
        productRepository.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));

        assertEquals(7, productRepository.findAll().size());

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(baseUrl + "/search" +
                "?minPrice=1000&brand=Apple&onSale=true", Map.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, List<Map<String, Object>>> groupedProducts = responseEntity.getBody();

        assertNotNull(groupedProducts);
        assertFalse(groupedProducts.isEmpty());
        assertTrue(groupedProducts.containsKey("apple"));
        assertEquals(2, groupedProducts.get("apple").size());
        assertProductsSortedByPrice(groupedProducts.get("apple"));
    }


    private void assertProductsSortedByPrice(List<Map<String, Object>> products) {
        Double previousPrice = null;
        for (Map<String, Object> product : products) {
            Double currentPrice = (Double) product.get("price");
            if (previousPrice != null) {
                assertTrue(currentPrice >= previousPrice, "Products are not sorted by price");
            }
            previousPrice = currentPrice;
        }
    }
}