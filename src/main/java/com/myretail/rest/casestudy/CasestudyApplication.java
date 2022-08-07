package com.myretail.rest.casestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.myretail.rest.casestudy.repository.ProductPricingRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = ProductPricingRepository.class)
public class CasestudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasestudyApplication.class, args);
	}

}
