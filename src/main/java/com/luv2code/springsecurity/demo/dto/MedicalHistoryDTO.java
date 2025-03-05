package com.luv2code.springsecurity.demo.dto;

import com.luv2code.springsecurity.demo.entity.MedHistory;

public class MedicalHistoryDTO {
	private int id;
	private String diagnosis;
    private String treatment;
	
    public MedicalHistoryDTO() {
		
	}
    
	public MedicalHistoryDTO(String diagnosis, String treatment,int id) {
		this.diagnosis = diagnosis;
		this.treatment = treatment;
		this.id = id;
	}
	
	public MedicalHistoryDTO(MedHistory medHistory) {
        this.id = medHistory.getId();
        this.diagnosis = medHistory.getDiagnosis();
        this.treatment = medHistory.getTreatment();
    }
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
}
