package com.myretail.rest.casestudy.resource;

public class PriceDto {
    private double value;
    private String currency_code;
    
    public PriceDto(double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }
    public String getCurrency_code() {
        return currency_code;
    }
    public double getValue() {
        return value;
    }
    
}
