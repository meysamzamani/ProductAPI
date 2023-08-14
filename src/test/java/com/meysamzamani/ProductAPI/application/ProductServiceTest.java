package com.meysamzamani.ProductAPI.application;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import com.meysamzamani.ProductAPI.presentation.dto.GroupedProductDTO;
import com.meysamzamani.ProductAPI.presentation.dto.ProductUpdateDTO;
import com.meysamzamani.ProductAPI.presentation.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    ProductService productService;

    @BeforeEach
    void setup() {
        productService = new ProductService(productRepository);
    }

    @Test
    void whenGetProducts_thenVerifyFindAll() {
        productService.getProducts();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void whenCreateProduct_thenVerifySave() {
        Product product = new Product("iPhone14Pro", 1100.00, "Apple", true);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.createProduct(product);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void whenDeleteProduct_thenVerifyHierarchy() {
        Long productId = 10L;
        Product product = new Product(productId, "iPhone14Pro", 1100.00, "Apple", true);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void givenId_whenDeleteProduct_thenThrowsNotFound() {
        Long productId = 10L;

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void whenUpdateProduct_thenVerifySaveAndEquals() {
        Long productId = 10L;
        Product existingProduct = new Product(productId, "iPhone14Pro", 1100.00, "Apple", true);

        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("iPhone13Pro");

        Product updatedProduct = productService.updateProduct(productId, updateDTO);

        assertNotNull(updatedProduct);
        assertEquals(updateDTO.getName(), updatedProduct.getName());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void givenId_whenUpdateProduct_thenThrowsNotFound() {
        Long productId = 10L;

        when(productRepository.findById(eq(productId))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            ProductUpdateDTO updateDTO = new ProductUpdateDTO();
            updateDTO.setName("iPhone13Pro");
            productService.updateProduct(productId, updateDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void whenGetProduct_thenVerifyFindById() {
        Long productId = 10L;
        Product product = new Product(productId, "iPhone14Pro", 1100.00, "Apple", true);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.getProduct(productId);

        assertNotNull(result);
        assertEquals(product, result);

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void whenGetProduct_thenThrowsNotFound() {
        Long productId = 10L;

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProduct(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void whenSearchProduct_thenVerifyFindAll() {
        Product product1 = new Product(1L, "Product A", 100.0, "Brand A", true);
        Product product2 = new Product(2L, "Product B", 150.0, "Brand B", false);

        when(productRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Arrays.asList(product1, product2));

        Map<String, List<GroupedProductDTO>> result = productService.searchProducts(null, null, null, null, null);

        verify(productRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

}