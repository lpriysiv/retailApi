package com.myretail.rest.casestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.myretail.rest.casestudy.exception.InvalidArgsException;
import com.myretail.rest.casestudy.exception.ResourceNotFoundException;
import com.myretail.rest.casestudy.exception.ServiceException;
import com.myretail.rest.casestudy.repository.ProductPricingRepository;
import com.myretail.rest.casestudy.resource.ProductPricing;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductPricingRepository productPricingRepository;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void whenNonNumericId_Is_Provided_ThrowsException() {
        assertThrows(InvalidArgsException.class, ()-> {
            productService.getProductById(("id1"));
        });
    }

    @Test
    public void whenProducNottInRepository_ThrowsResourceNotFound() {
        when(productPricingRepository.findById(anyLong()))
        .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.getProductById(("1"));
        });
    }

    @Test
    public void whenProductInRepository_ReturnsProductPricing() {
        when(productPricingRepository.findById(anyLong()))
        .thenReturn(Optional.of(new ProductPricing(1L, 1.99, "CAD")));
        assertTrue(productService.getProductById("1") instanceof ProductPricing);
    }

    @Test
    public void whenRestTemplate_Returns404_ThrowsResourceNotFoundException() {
        HttpClientErrorException httpEx = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(anyString(), any(), anyMap())).thenThrow(httpEx);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.getProductTitleById("1");
        });
    }

    @Test
    public void whenRestTemplate_Returns401_ThrowsInvalidArgsException() {
        HttpClientErrorException httpEx = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(anyString(), any(), anyMap())).thenThrow(httpEx);
        assertThrows(InvalidArgsException.class, ()-> {
            productService.getProductTitleById("1");
        });
    }

    @Test
    public void whenRestTemplate_Returns500_ThrowsServiceException() {
        HttpClientErrorException httpEx = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(anyString(), any(), anyMap())).thenThrow(httpEx);
        assertThrows(ServiceException.class, ()-> {
            productService.getProductTitleById("1");
        });
    }

    @Test
    public void whenRestTemplate_ReturnsInvalidJsonResponse_ThrowsServiceException() {
        when(restTemplate.getForEntity(anyString(), any(), anyMap())).thenReturn(new ResponseEntity<>("{\"data\":{product:null}}",HttpStatus.OK));
        assertThrows(ServiceException.class, ()-> {
            productService.getProductTitleById("1");
        });
    }

    @Test
    public void whenJsonResponse_DoesNotContainTitle_ThrowsServiceException() {
        when(restTemplate.getForEntity(anyString(), any(), anyMap())).thenReturn(new ResponseEntity<>("{\"data\":{\"product\":{\"tcin\":1234}}}",HttpStatus.OK));
        assertThrows(ServiceException.class, ()-> {
            productService.getProductTitleById("1");
        });
    }

    @Test
    public void whenJsonResponse_ContainsTitle_ReturnsTitle() {
        when(restTemplate.getForEntity(anyString(), any(), anyMap()))
            .thenReturn(new ResponseEntity<>("{\"data\":{\"product\":{\"tcin\":\"1234\",\"item\":{\"product_description\":{\"title\":\"Test Product\"}}}}}",HttpStatus.OK));
        assertEquals("Test Product", productService.getProductTitleById("1"));
    }
    
    @Test
    public void whenPriceIsUpdated_CallsSaveProduct() {
        when(productPricingRepository.findById(anyLong()))
            .thenReturn(Optional.of(new ProductPricing(1L, 1.99, "CAD")));
        productService.updatePrice("1","2.99");
        verify(productPricingRepository).save(any(ProductPricing.class));
    }

    @Test
    public void whenNonNumericPrice_Is_Provided_ThrowsException() {
        assertThrows(InvalidArgsException.class, ()-> {
            productService.updatePrice("100","foo1");
        });
    }
}
