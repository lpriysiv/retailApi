package com.myretail.rest.casestudy.resource;

public class Error {
    private String status_code;
    public String getStatus_code() {
        return status_code;
    }
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
