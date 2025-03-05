package com.luv2code.springsecurity.demo.dto;

import java.util.List;

public class ClinicWithFacilitiesDTO {
	private int clinicId;
    private String clinicName;
    private List<FacilityDTO> facilities;
    
	public ClinicWithFacilitiesDTO() {
	}
	public ClinicWithFacilitiesDTO(int clinicId, String clinicName, List<FacilityDTO> facilities) {
		this.clinicId = clinicId;
		this.clinicName = clinicName;
		this.facilities = facilities;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public List<FacilityDTO> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<FacilityDTO> facilities) {
		this.facilities = facilities;
	}
    
    
}
