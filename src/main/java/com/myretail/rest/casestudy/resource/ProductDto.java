package com.myretail.rest.casestudy.resource;

public class ProductDto {
    private Long id;
    public Long getId() {
        return id;
    }

    private String name;
    public String getName() {
        return name;
    }

    private PriceDto current_price;

    public PriceDto getCurrent_price() {
        return current_price;
    }

    public ProductDto(Long id, String name, double price, String currencyCode) {
        this.id = id;
        this.name = name;
        this.current_price = new PriceDto(price, currencyCode);
    }

}
