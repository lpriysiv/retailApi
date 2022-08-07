package com.myretail.rest.casestudy.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.rest.casestudy.exception.InvalidArgsException;
import com.myretail.rest.casestudy.exception.ResourceNotFoundException;
import com.myretail.rest.casestudy.exception.ServiceException;
import com.myretail.rest.casestudy.repository.ProductPricingRepository;
import com.myretail.rest.casestudy.resource.ProductPricing;


@Service
public class ProductService {

    RestTemplate restTemplate;

    ProductPricingRepository productRepository;

    Logger log = LoggerFactory.getLogger(ProductService.class);

    private String productDetailUrl = "https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1?key={key}&tcin={tcin}";

    public ProductService(RestTemplate restTemplate, ProductPricingRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

    public ProductPricing getProductById(String id) {
        log.debug("Product Id provided:{}",id);
        Long productId = validateProductId(id); 
        ProductPricing productPricing = productRepository.findById(productId).orElse(null);
        if (productPricing == null) {
            throw new ResourceNotFoundException("Product Id not found");
        }
        return productPricing;   
     
    }
    
    public String getProductTitleById(String id) {      
        Map<String, String> params = new HashMap<>();
        params.put("key", "3yUxt7WltYG7MFKPp7uyELi1K40ad2ys");
        params.put("tcin",id);
        try{
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(productDetailUrl, String.class, params);
            String title = getTitle(responseEntity.getBody());
            return title;
        } catch (HttpClientErrorException ex) {
            log.error("Exception thrown fetching title for product {}: {} ", id, ex.getMessage());
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ResourceNotFoundException("Product Id not found");
            } else if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new InvalidArgsException("Invalid request parameters:" + id);
            } else {
                throw new ServiceException("Internal error processing request with parameter:" + id);
            }
        }
    }

    private String getTitle(String jsonBody) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode productNode;
        try {
            productNode = mapper.readTree(jsonBody).at("/data/product");
            JsonNode descriptionNode = productNode.at("/item/product_description");
            return descriptionNode.get("title").textValue();
        } catch (JsonProcessingException jpe) {
            log.error("Failure parsing product detail json body:{} {}", jsonBody, jpe);
            throw new ServiceException("Internal error parsing product description response");
        } catch (Exception ex) {
            log.error("Error getting product title from json body:{} {}", jsonBody, ex);
            throw new ServiceException("Unknown error parsing product response");
        }
    }

    public ProductPricing updatePrice(String id, String newPrice) {
        Long product_id = validateProductId(id);
        double price = validatePrice(newPrice);
        Optional<ProductPricing> entity = productRepository.findById(product_id);
        ProductPricing productPricing = entity.get();
        productPricing.setPrice(price);
        return productRepository.save(productPricing);
    }


    private Long validateProductId(String id) {
        Long productId;
        try {
            productId = Long.parseLong(id);
        }catch (Exception ex) {
            throw new InvalidArgsException("Invalid product id provided");
        }
        return productId;
    }

    private double validatePrice(String newPrice) {
        double price;
        try {
            price = Double.parseDouble(newPrice);
        }catch (Exception ex) {
            throw new InvalidArgsException("Invalid price field");
        }
        return price;
    }
    
}
