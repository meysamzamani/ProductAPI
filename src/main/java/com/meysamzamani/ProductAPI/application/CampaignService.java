package com.meysamzamani.ProductAPI.application;

import com.meysamzamani.ProductAPI.domain.Campaign;
import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.infrastructure.persistence.CampaignRepository;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import com.meysamzamani.ProductAPI.presentation.dto.CampaignRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, ProductRepository productRepository) {
        this.campaignRepository = campaignRepository;
        this.productRepository = productRepository;
    }

    public Campaign createCampaign(CampaignRequestDTO campaignDTO) {

        Set<Product> products = new HashSet<>();
        for (Long productId : campaignDTO.getProductIds()) {
            Optional<Product> product = productRepository.findById(productId);
            product.ifPresent(products::add);
        }
        Campaign campaign = new Campaign(campaignDTO.getStartDate(), campaignDTO.getEndDate(), products);

        return campaignRepository.save(campaign);
    }
    
}
