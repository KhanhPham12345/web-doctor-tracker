package com.luv2code.springsecurity.demo.dto;

public class FacilitySearchRequest {
	private String country;
	private String regionState;
	private String city;
	private String hospitalName;
	
	public FacilitySearchRequest() {
		
	}
	
	public FacilitySearchRequest(String country, String regionaState, String city, String hospitalName) {
		this.country = country;
		this.regionState = regionaState;
		this.city = city;
		this.hospitalName = hospitalName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegionState() {
		return regionState;
	}
	public void setRegionState(String regionState) {
		this.regionState = regionState;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	
}
