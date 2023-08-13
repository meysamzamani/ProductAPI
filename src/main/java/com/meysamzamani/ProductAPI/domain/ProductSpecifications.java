package com.meysamzamani.ProductAPI.domain;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> minPrice(Double minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.le(root.get("price"), maxPrice);
    }

    public static Specification<Product> brandEquals(String brand) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Product> onSaleEquals(Boolean onSale) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("onSale"), onSale);
    }
}
