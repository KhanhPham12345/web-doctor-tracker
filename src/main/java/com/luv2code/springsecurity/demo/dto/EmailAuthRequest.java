package com.luv2code.springsecurity.demo.dto;

public class EmailAuthRequest {
	String email;
	
	public EmailAuthRequest(String email) {
		this.email = email;
	}
	
	public EmailAuthRequest() {
		
	}

	// Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
