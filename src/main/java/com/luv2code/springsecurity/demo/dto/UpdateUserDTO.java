package com.luv2code.springsecurity.demo.dto;

public class UpdateUserDTO {
	private String name;
    private String address;
    private String phoneNumber;
    private String gender;
    
    public UpdateUserDTO() {
    	
    }
    
	public UpdateUserDTO(String name, String address, String phoneNumber, String gender) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}

