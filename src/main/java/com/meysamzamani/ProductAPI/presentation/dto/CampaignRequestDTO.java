package com.meysamzamani.ProductAPI.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class CampaignRequestDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private List<Long> productIds;

    public CampaignRequestDTO(LocalDate startDate, LocalDate endDate, List<Long> productIds) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.productIds = productIds;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
