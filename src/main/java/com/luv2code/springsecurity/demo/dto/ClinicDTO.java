package com.luv2code.springsecurity.demo.dto;

public interface ClinicDTO {
	Long getId();
    String getName();
    String getAddress();
    String getPhone();
    String getDescription();
    String getCreatedAt();
    String getUpdatedAt(); 
    Long getDoctorCount();
}
