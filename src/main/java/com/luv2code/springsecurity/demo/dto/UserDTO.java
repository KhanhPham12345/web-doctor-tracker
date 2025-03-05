package com.luv2code.springsecurity.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.luv2code.springsecurity.demo.entity.MedHistory;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;



public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String avatar;
    private String phone;
    private List<MedicalHistoryDTO> medicalHistories;
    private List<StatusDTOForUser> statuses;
    
    public UserDTO() {
    	
    }
    
	public UserDTO(int id, String name, String email, String gender, String avatar, String phone,
			List<MedicalHistoryDTO> medicalHistories) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.avatar = avatar;
		this.phone = phone;
		this.medicalHistories = medicalHistories;
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhoneNumber();
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
        this.medicalHistories = user.getMedicalHistories().stream()
                .map(MedicalHistoryDTO::new)
                .collect(Collectors.toList());
        this.statuses = user.getStatus().stream()
                .map(StatusDTOForUser::new)
                .collect(Collectors.toList());
    }
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<MedicalHistoryDTO> getMedicalHistories() {
		return medicalHistories;
	}
	public void setMedicalHistories(List<MedicalHistoryDTO> medicalHistories) {
		this.medicalHistories = medicalHistories;
	}

	public List<StatusDTOForUser> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<StatusDTOForUser> statuses) {
		this.statuses = statuses;
	}
	
}