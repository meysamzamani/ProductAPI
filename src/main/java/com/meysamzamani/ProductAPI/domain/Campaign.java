package com.meysamzamani.ProductAPI.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class Campaign {

    @Id
    @SequenceGenerator(name = "campaign_sequence", sequenceName = "campaign_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence")
    private long id;
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "campaign_product",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public Campaign() {
    }

    public Campaign(LocalDate startDate, LocalDate endDate, Set<Product> products) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
