package com.meysamzamani.ProductAPI.infrastructure.persistence;

import com.meysamzamani.ProductAPI.domain.Campaign;
import com.meysamzamani.ProductAPI.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByProducts(Product products);

}
