package com.luv2code.springsecurity.demo.dto;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.MedHistory;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;

public class StatusDTO {
	private int id;
	private String title;
	private Boolean confirmByDoctor;
	private String createdAt;
	private String updatedAt;
	
	private String patientName;
	private String patientGender;
	private String patientAddress;
	private List<MedHistory> patientMedicalHistory;
	
	
	
	public StatusDTO(int id, String title, Boolean confirmByDoctor, String createdAt, String updatedAt, User patient) {
		this.id = id;
		this.title = title;
		this.confirmByDoctor = confirmByDoctor;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.patientName = patient.getName();
		this.patientGender = patient.getGender();
		this.patientAddress = patient.getAddress();
		this.patientMedicalHistory = patient.getMedicalHistories();
	}
	
	public StatusDTO(Status status) {
		this.id = status.getId();
		this.title = status.getTitle();
		this.confirmByDoctor = status.getConfirmByDoctor();
		this.createdAt = status.getCreatedAt().toString();
		this.updatedAt = status.getUpdatedAt().toString();
		this.patientName = status.getPatient().getName();
		this.patientGender = status.getPatient().getGender();
		this.patientAddress = status.getPatient().getAddress();
		this.patientMedicalHistory = status.getPatient().getMedicalHistories();
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
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public List<MedHistory> getPatientMedicalHistory() {
		return patientMedicalHistory;
	}
	public void setPatientMedicalHistory(List<MedHistory> patientMedicalHistory) {
		this.patientMedicalHistory = patientMedicalHistory;
	}
	
}
