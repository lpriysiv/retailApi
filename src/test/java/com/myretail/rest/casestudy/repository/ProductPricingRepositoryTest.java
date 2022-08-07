package com.myretail.rest.casestudy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.myretail.rest.casestudy.resource.ProductPricing;

@DataMongoTest
public class ProductPricingRepositoryTest {

    @Autowired
    ProductPricingRepository productPricingRepository;

    @BeforeEach
    public void setup() {
        ProductPricing productPricing = new ProductPricing(1L, 10.99, "USD");
        productPricingRepository.save(productPricing);
    }

    @Test
    public void findById_ReturnsProductPricing() {
        Optional<ProductPricing> productPricing = productPricingRepository.findById(1L);
        assertEquals(1L, productPricing.get().getId());
        assertEquals(10.99, productPricing.get().getPrice());
        assertEquals("USD", productPricing.get().getCurrencyCode());
    }

    @AfterEach
    public void teardown() {
        productPricingRepository.deleteById(1L);
    }

}