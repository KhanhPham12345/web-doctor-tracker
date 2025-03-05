package com.luv2code.springsecurity.demo.dto;

import com.luv2code.springsecurity.demo.entity.Status;

public class UpdateBookingRequest {
	private int statusId;
	private boolean confirmedByDoctor;
	private String note;
	
	public UpdateBookingRequest() {
		
	}
	
	public UpdateBookingRequest(Status status) {
		this.statusId = status.getId();
		this.confirmedByDoctor = status.getConfirmByDoctor();
		this.note = status.getTitle();
	}
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public boolean isConfirmedByDoctor() {
		return confirmedByDoctor;
	}
	public void setConfirmedByDoctor(boolean confirmedByDoctor) {
		this.confirmedByDoctor = confirmedByDoctor;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
