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
            Product iPhone14pro = new Product("iPhone14Pro", 1100.00, "Apple", false);
            Product slmini = new Product("SoundLink Mini", 149.95, "Bose", false);
            Product pixel7a = new Product("Pixel7A", 800.90, "Google", false);
            Product slflex = new Product("SoundLink Flex", 164.95, "bose", false);
            Product Pixel6a = new Product("Pixel61", 345.00, "gOogle", false);
            Product iPhoneSE2 = new Product("iPhoneSE2", 750.90, "apple", false);
            Product appleWatchUltra = new Product("AppleWatchUltra", 999.00, "aPple", false);
            repository.saveAll(List.of(iPhone14pro, slmini, pixel7a, slflex, Pixel6a, iPhoneSE2, appleWatchUltra));
        };
    }

}
