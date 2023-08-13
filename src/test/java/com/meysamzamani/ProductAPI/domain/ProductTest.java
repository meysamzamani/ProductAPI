package com.meysamzamani.ProductAPI.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
    }

    @Test
    public void givenProperties_whenSet_thenShouldEquals() {

        String name = "iPhone14Pro";
        double price = 1100.00;
        String brand = "Apple";
        boolean onSale = false;

        product.setName(name);
        product.setPrice(price);
        product.setBrand(brand);
        product.setOnSale(onSale);

        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(brand, product.getBrand());
        assertEquals(onSale, product.isOnSale());
    }

    @Test
    public void givenProperties_whenToString_thenShouldEqualExpected() {

        String name = "iPhone14Pro";
        double price = 1100.21;
        String brand = "Apple";
        boolean onSale = false;

        product.setName(name);
        product.setPrice(price);
        product.setBrand(brand);
        product.setOnSale(onSale);

        String expectedString = "Product{" +
                "id=null, " +
                "name='iPhone14Pro', " +
                "price=1100.21, " +
                "brand='Apple', " +
                "onSale=false" +
                "}";

        assertEquals(expectedString, product.toString());
    }

}