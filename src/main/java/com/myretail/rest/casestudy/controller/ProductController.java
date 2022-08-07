package com.myretail.rest.casestudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.rest.casestudy.resource.ProductDto;
import com.myretail.rest.casestudy.resource.ProductPricing;
import com.myretail.rest.casestudy.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") String id) {
        ProductPricing productPricing = productService.getProductById(id);
        String title = productService.getProductTitleById(id);
        return new ProductDto(productPricing.getId(), title, productPricing.getPrice(),productPricing.getCurrencyCode());
    }

    @PutMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") String id, @RequestParam String price) {   
        ProductPricing productPricing = productService.updatePrice(id, price);
        String title = productService.getProductTitleById(id);
        return new ProductDto(productPricing.getId(), title, productPricing.getPrice(),productPricing.getCurrencyCode());
    }
}