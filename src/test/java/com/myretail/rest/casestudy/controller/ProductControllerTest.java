package com.myretail.rest.casestudy.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.myretail.rest.casestudy.exception.InvalidArgsException;
import com.myretail.rest.casestudy.exception.ResourceNotFoundException;
import com.myretail.rest.casestudy.exception.ServiceException;
import com.myretail.rest.casestudy.resource.ProductPricing;
import com.myretail.rest.casestudy.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {  
    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void whenInvalidArgumentsExceptionThrown_ReturnBadRequest() throws Exception{
        when(productService.getProductById(anyString()))
            .thenThrow(InvalidArgsException.class);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/products/{id}","blah"))
            .andExpect(status().isBadRequest());         
    }

    @Test
    public void whenResourceNotFound_ReturnsNotFound() throws Exception{
        when(productService.getProductById(anyString()))
            .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/products/null"))
            .andExpect(status().isNotFound());              
    }

    @Test
    public void whenServerErrorThrown_ReturnsInternalError() throws Exception{
        when(productService.getProductById(anyString()))
            .thenThrow(ServiceException.class);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/products/null"))
            .andExpect(status().isInternalServerError());              
    }


    @Test
    public void whenProductFound_ReturnsProductDto() throws Exception{
        ProductPricing productPricing = new ProductPricing(1L, 4.99, "CAD");
        when(productService.getProductById(anyString()))
            .thenReturn(productPricing);
        when(productService.getProductTitleById(anyString()))
            .thenReturn("Test Product");    
        mockMvc.perform(MockMvcRequestBuilders
            .get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Test Product")))
            .andExpect(jsonPath("$.current_price.value", is(4.99)))
            .andExpect(jsonPath("$.current_price.currency_code", is("CAD")));
      
    }

    @Test
    public void whenProductUpdated_ReturnsProductDto() throws Exception{
        ProductPricing productPricing = new ProductPricing(1L, 5.99, "CAD");
        when(productService.updatePrice(anyString(), anyString()))
            .thenReturn(productPricing);
        when(productService.getProductTitleById(anyString()))
            .thenReturn("Test Product");    
        
        mockMvc.perform(MockMvcRequestBuilders
            .put("/products/1").param("price", "5.99"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Test Product")))
            .andExpect(jsonPath("$.current_price.value", is(5.99)))
            .andExpect(jsonPath("$.current_price.currency_code", is("CAD")));
      
    }
}
