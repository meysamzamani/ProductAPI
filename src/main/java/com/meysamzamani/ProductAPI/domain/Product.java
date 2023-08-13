package com.meysamzamani.ProductAPI.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;
    @Min(value = 0, message = "Price should not be less than 0")
    private double price;
    @NotBlank(message = "Brand cannot be blank")
    @Column(nullable = false)
    private String brand;
    private boolean onSale;

    public Product() {
    }

    public Product(String name, double price, String brand, boolean onSale) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.onSale = onSale;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", onSale=" + onSale +
                '}';
    }
}
