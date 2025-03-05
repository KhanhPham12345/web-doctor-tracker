package com.luv2code.springsecurity.demo.dto;

public class FacilityDTO {
	private int facilityId;
	private String facilityName;
	private String unitNo;
	private String block;
	private String street;
	private String buildingName;
	private String regionState;
	private String city;
	private String country;
	private String postalCode;
	private int totalDoctor;
	private int totalSpeacialty;
	private int totalSubSpecialty;
	
	public FacilityDTO() {
		
	}
	
	public FacilityDTO(int facilityId, String facilityName, String unitNo, String block, String street,
			String buildingName, String regionState, String city, String country, String postalCode, int totalDoctor,
			int totalSpeacialty, int totalSubSpecialty) {
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.unitNo = unitNo;
		this.block = block;
		this.street = street;
		this.buildingName = buildingName;
		this.regionState = regionState;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
		this.totalDoctor = totalDoctor;
		this.totalSpeacialty = totalSpeacialty;
		this.totalSubSpecialty = totalSubSpecialty;
	}

	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public int getTotalDoctor() {
		return totalDoctor;
	}
	public void setTotalDoctor(int totalDoctor) {
		this.totalDoctor = totalDoctor;
	}
	public int getTotalSpeacialty() {
		return totalSpeacialty;
	}
	public void setTotalSpeacialty(int totalSpeacialty) {
		this.totalSpeacialty = totalSpeacialty;
	}
	public int getTotalSubSpecialty() {
		return totalSubSpecialty;
	}
	public void setTotalSubSpecialty(int totalSubSpecialty) {
		this.totalSubSpecialty = totalSubSpecialty;
	}
	
	
}
