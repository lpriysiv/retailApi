package com.myretail.rest.casestudy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.myretail.rest.casestudy.exception.InvalidArgsException;
import com.myretail.rest.casestudy.exception.ResourceNotFoundException;
import com.myretail.rest.casestudy.exception.ServiceException;
import com.myretail.rest.casestudy.resource.Error;

@RestControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(value=ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error handleResourceNotFound(Exception ex, WebRequest request) {
        Error error = new Error();
        error.setStatus_code("404");
        error.setMessage("Product not found");
        return error;
    }

    @ExceptionHandler(value=InvalidArgsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error handleInvalidArguments(Exception ex, WebRequest request) {
        Error error = new Error();
        error.setStatus_code("400");
        error.setMessage("Invalid value provided. Expected numeric value");
        return error;
    }

    @ExceptionHandler(value=ServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleInternalErrors(Exception ex, WebRequest request) {
        Error error = new Error();
        error.setStatus_code("500");
        error.setMessage("Service Error occurred");
        return error;
    }

}
