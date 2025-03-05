package com.luv2code.springsecurity.demo.dto;

public class LockAccountRequest {
    private String description;

    public LockAccountRequest() {
    }

    public LockAccountRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
