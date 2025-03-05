package com.luv2code.springsecurity.demo.dto;

public class SearchRequest {
	private String name;
	private String email;
	private String clinic;
	private String place;
	private String specialization;
	
	public SearchRequest(String name, String email, String clinic, String place, String specialization) {
		this.name = name;
		this.email = email;
		this.clinic = clinic;
		this.place = place;
		this.specialization = specialization;
	}
	
	public SearchRequest() {
	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClinic() {
		return clinic;
	}
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

}
