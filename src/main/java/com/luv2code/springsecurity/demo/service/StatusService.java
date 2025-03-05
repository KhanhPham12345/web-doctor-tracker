package com.luv2code.springsecurity.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.ClinicRepository;
import com.luv2code.springsecurity.demo.dao.ScheduleRepository;
import com.luv2code.springsecurity.demo.dao.StatusesRepository;
import com.luv2code.springsecurity.demo.dao.UserRepository;
import com.luv2code.springsecurity.demo.dto.UpdateBookingRequest;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.entity.Schedule;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;

@Service
public class StatusService {
	
	@Autowired
	private StatusesRepository statusRepository;

	public List<Status> getStatusesByDoctorId(int doctorId) {
		return statusRepository.findByDoctorId(doctorId);
	}

	public Status findById(int statusId) {
		Optional<Status> result = statusRepository.findById(statusId);
		Status theStatus = null;
		if (result.isPresent()) {
			theStatus = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find user with given id - " + statusId);
		}
		return theStatus;

	}
	
	public void updateBooking(UpdateBookingRequest request, int doctorId) {
		Status status = statusRepository.findById(request.getStatusId()).orElseThrow(()-> new RuntimeException("Status not found."));
		
		if(status.getDoctor().getId() != doctorId) {
			throw new RuntimeException("Access denied: You cannot update this booking.");
		}
		
		status.setConfirmByDoctor(request.isConfirmedByDoctor());
		
		// If the appointment is canceled (i.e. not confirmed), set the cancellation note
        if (!request.isConfirmedByDoctor()) {
            status.setTitle(request.getNote());
        }
        
        // Save the updated status
        statusRepository.save(status);
	}

	public void save(Status status) {
		statusRepository.save(status);
	}
	
}
