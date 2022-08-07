package com.myretail.rest.casestudy.resource;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductPricing {

    private Long id;

    private double price;

    private String currencyCode;

    public ProductPricing(Long id, double price, String currencyCode) {
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currency) {
        this.currencyCode = currency;
    }
}
