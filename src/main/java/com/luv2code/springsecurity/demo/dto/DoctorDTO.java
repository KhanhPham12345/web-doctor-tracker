package com.luv2code.springsecurity.demo.dto;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.User;

public class DoctorDTO {
	private int doctorId;
    private String fullname;
    private String gender;
    private String profileImageUrl;
    private String practiceLocations;
    private List<String> specialties;
    private Integer consultFee;
    private List<String> qualifications;
    private List<String> workExperiences;
    private String background;
    
    
    public DoctorDTO() {
    	
    }
    
   
	public DoctorDTO(int doctorId, String fullname, String gender, String profileImageUrl,
			String practiceLocations, List<String> specialties, Integer consultFee, List<String> qualifications,
			List<String> workExperiences, String background) {
		this.doctorId = doctorId;
		this.fullname = fullname;
		this.gender = gender;
		this.profileImageUrl = profileImageUrl;
		this.practiceLocations = practiceLocations;
		this.specialties = specialties;
		this.consultFee = consultFee;
		this.qualifications = qualifications;
		this.workExperiences = workExperiences;
		this.background = background;
	}
	
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public String getPracticeLocations() {
		return practiceLocations;
	}
	public void setPracticeLocations(String row) {
		this.practiceLocations = row;
	}
	public List<String> getSpecialties() {
		return specialties;
	}
	public void setSpecialties(List<String> specialties) {
		this.specialties = specialties;
	}
	public Integer getConsultFee() {
		return consultFee;
	}
	public void setConsultFee(Integer integer) {
		this.consultFee = integer;
	}
	public List<String> getQualifications() {
		return qualifications;
	}
	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}
	public List<String> getWorkExperiences() {
		return workExperiences;
	}
	public void setWorkExperiences(List<String> workExperiences) {
		this.workExperiences = workExperiences;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}

}
