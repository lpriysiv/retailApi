package com.myretail.rest.casestudy.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.myretail.rest.casestudy.resource.ProductPricing;

@Repository
public interface ProductPricingRepository extends MongoRepository<ProductPricing, Long> {
    
    @Query("{id:?0}")
    public Optional<ProductPricing> findById(Long id);   

}
