package com.luv2code.springsecurity.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.ClinicRepository;
import com.luv2code.springsecurity.demo.dao.ScheduleRepository;
import com.luv2code.springsecurity.demo.dao.StatusesRepository;
import com.luv2code.springsecurity.demo.dao.UserRepository;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.entity.Schedule;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;

@Service
public class BookingService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StatusesRepository statusRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Transactional
	public String bookAppointment(String doctorName, String timeSlot, double consultationFee, String patientName,
			String gender, String phoneNumber, String dob, String address, String reason, String clinicName, int patientId) {
		try {
			// Validate the provided doctor and time slot
			Schedule schedule = scheduleRepository.findByDoctorNameAndTime(doctorName, timeSlot)
					.orElseThrow(() -> new RuntimeException("Doctor or time slot not available"));

			// Check if the booking limit has been reached
			if (schedule.getSumBooking() >= schedule.getMaxBooking()) {
				throw new RuntimeException("No slots available for this time");
			}
			
			// Fetch the clinic by doctor_id (using the doctor_id from the schedule)
            Optional<Clinic> clinicOpt = clinicRepository.findClinicByDoctorIdAndClinic(schedule.getDoctor().getId(),clinicName);
            
            if (!clinicOpt.isPresent()) {
                throw new RuntimeException("Clinic not found for this doctor");
            }
            
            Clinic clinic = clinicOpt.get();
            
            // Get the user-patient
            Optional<User> result = userRepository.findById(patientId);
    		
    		User thePatient = null;
    		
    		if (result.isPresent()) {
    			thePatient = result.get();
    		}
    		else {
    			// we didn't find the employee
    			throw new RuntimeException("Did not find user with given id - " + patientId);
    		}
  
			
			// Record booking status
			Status status = new Status();
			status.setTitle("Appointment booked for: " + reason);
			status.setDoctor(schedule.getDoctor());
			status.setConfirmByDoctor(false);
			status.setCreatedAt(LocalDateTime.now());
			status.setUpdatedAt(LocalDateTime.now());
			status.setClinic(clinic);
			status.setPatient(thePatient);
			
			// Update the booking count
			schedule.setSumBooking(schedule.getSumBooking() + 1);
			schedule.setUpdatedAt(LocalDateTime.now());
			scheduleRepository.save(schedule);

			statusRepository.save(status);

			return "Appointment booked successfully for " + patientName;
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
