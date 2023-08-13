package com.meysamzamani.ProductAPI.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GroupedProductDTO {

    private long id;
    private String name;
    private double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String event;

    public GroupedProductDTO(long id, String name, double price, String event) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getEvent() {
        return event;
    }
}
