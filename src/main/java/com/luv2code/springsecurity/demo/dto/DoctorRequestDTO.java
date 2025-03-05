package com.luv2code.springsecurity.demo.dto;

import java.util.List;

public class DoctorRequestDTO {
	private String username;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private String avatar;
    private String gender;
    private String email;
    private String description;
    private List<String> clinic;
    private List<String> specializations;
    private List<String> workExperienceDescriptions;
    private String background;
    private Integer consultfee;
    private String place;
    
    public DoctorRequestDTO() {
    	
    }
    
	public DoctorRequestDTO(String username, String password, String name, String address, String phoneNumber,
			String avatar, String gender, String email, String description, List<String> clinic, List<String> specializations,
			List<String> workExperienceDescriptions, String background, int consultfee, String place) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.avatar = avatar;
		this.gender = gender;
		this.email = email;
		this.description = description;
		this.clinic = clinic;
		this.specializations = specializations;
		this.workExperienceDescriptions = workExperienceDescriptions;
		this.background = background;
		this.consultfee = consultfee;
		this.place = place;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getClinic() {
		return clinic;
	}

	public void setClinic(List<String> clinic) {
		this.clinic = clinic;
	}

	public List<String> getSpecializations() {
		return specializations;
	}
	public void setSpecializations(List<String> specializationIds) {
		this.specializations = specializationIds;
	}
	public List<String> getWorkExperienceDescriptions() {
		return workExperienceDescriptions;
	}
	public void setWorkExperienceDescriptions(List<String> workExperienceDescriptions) {
		this.workExperienceDescriptions = workExperienceDescriptions;
	}
	
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getConsultfee() {
		return consultfee;
	}

	public void setConsultfee(int consultfee) {
		this.consultfee = consultfee;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "DoctorRequestDTO [username=" + username + ", password=" + password + ", name=" + name + ", address="
				+ address + ", phoneNumber=" + phoneNumber + ", avatar=" + avatar + ", gender=" + gender + ", email="
				+ email + ", description=" + description + ", clinic=" + clinic + ", specializations=" + specializations
				+ ", workExperienceDescriptions=" + workExperienceDescriptions + "]";
	}
	
}
