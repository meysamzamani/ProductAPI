package com.meysamzamani.ProductAPI.configuration;

import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.infrastructure.persistence.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository) {
        return args -> {
            Product meysam = new Product("Meysam", 10, "Amazon", true);
            Product elham = new Product("Elham", 11, "aMazon", false);
            Product ali = new Product("Ali", 12, "Google", true);
            Product saeid = new Product("Saeid", 12, "google", false);
            Product fereshteh = new Product("Fereshteh", 17, "amazon", true);
            Product sonix = new Product("Sonix", 10, "bose", false);
            Product mpower = new Product("MPower", 5, "amazon", true);
            repository.saveAll(List.of(meysam, elham, ali, saeid, fereshteh, sonix, mpower));
        };
    }

}
