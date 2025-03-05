package com.luv2code.springsecurity.demo.dto;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.MedHistory;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;

public class StatusDTOForUser {
	private int id;
	private String title;
	private Boolean confirmByDoctor;
	private String createdAt;
	private String updatedAt;
	private String doctorName;
	
	public StatusDTOForUser(Status status) {
		this.id = status.getId();
		this.title = status.getTitle();
		this.confirmByDoctor = status.getConfirmByDoctor();
		this.createdAt = status.getCreatedAt().toString();
		this.updatedAt = status.getUpdatedAt().toString();
		this.doctorName = status.getDoctor().getName();
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getConfirmByDoctor() {
		return confirmByDoctor;
	}
	public void setConfirmByDoctor(Boolean confirmByDoctor) {
		this.confirmByDoctor = confirmByDoctor;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
}
