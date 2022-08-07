package com.myretail.rest.casestudy.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException (String message) {
        super(message);
    }
    
}
