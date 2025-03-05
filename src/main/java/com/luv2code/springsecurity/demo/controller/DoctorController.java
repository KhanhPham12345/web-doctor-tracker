package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.dto.ResponseDTO;
import com.luv2code.springsecurity.demo.dto.StatusDTO;
import com.luv2code.springsecurity.demo.dto.UpdateBookingRequest;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.ClinicService;
import com.luv2code.springsecurity.demo.service.StatusService;
import com.luv2code.springsecurity.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	@Autowired
	private UserService userService;

	@Autowired
	private StatusService statusService;

	private String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	@GetMapping("/{doctorId}/patients")

	public ResponseEntity<?> getPatients(@PathVariable int doctorId,
			@RequestHeader("Authorization") String authorizationHeader) {
		// Extract the authenticated doctor's ID from the JWT
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			ResponseDTO<?> response = new ResponseDTO<>("Authorization header is missing or invalid", null,
					HttpStatus.BAD_REQUEST.value(), getCurrentTime());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

		try {
			String username = userService.getUsernameFromToken(token);
			User currentDoctor = userService.findByEmail(username);
			int userId = currentDoctor.getId();

			System.out.println("Current doctor id: " + userId);

			// Ensure the doctor can only access their own patients
			if (userId != doctorId) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(Collections.singletonMap("error", "Access denied: You can only view your own patients."));
			}

			// List<User> patients = userService.getPatientsByDoctorId(doctorId);
			List<Status> statuses = statusService.getStatusesByDoctorId(doctorId);

			List<StatusDTO> statusDTOs = statuses.stream()
					.map(status -> new StatusDTO(status.getId(), status.getTitle(), status.getConfirmByDoctor(),
							status.getCreatedAt().toString(),
							status.getUpdatedAt() != null ? status.getUpdatedAt().toString() : null,
							status.getPatient()))
					.collect(Collectors.toList());

			return ResponseEntity.ok(statusDTOs);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null,
					HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString()));
		}

	}
	
	@PostMapping("/updateBooking")
	public ResponseEntity<ResponseDTO<StatusDTO>> updateBooking(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody UpdateBookingRequest updateBookingRequest
			){
		//validate Authorization header
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDTO<>("Authorization header is missing or invalid",null,HttpStatus.FORBIDDEN.value(), getCurrentTime()));
		}
		
		String token = authorizationHeader.substring(7);
		
		try {
			String username = userService.getUsernameFromToken(token);
			User doctor = userService.findByEmail(username);
			int doctorId = doctor.getId();
			
			//Fetch the status entity by statusId
			Status status = statusService.findById(updateBookingRequest.getStatusId());
			if(status==null) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                    .body(new ResponseDTO<>("Status not found.", null, HttpStatus.BAD_REQUEST.value(), getCurrentTime()));
			}
			
			//Ensure only doctor can only update their own statuses
			if(status.getDoctor().getId() != doctorId) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ResponseDTO<>("Access denied: You cannot update this booking.", null, HttpStatus.FORBIDDEN.value(), getCurrentTime()));
			}
			
			//Update status fields
			status.setConfirmByDoctor(updateBookingRequest.isConfirmedByDoctor());
			
			if(!updateBookingRequest.isConfirmedByDoctor()) {
				status.setNote(updateBookingRequest.getNote());
			}
			
			//Save updated status
			statusService.save(status);
			
			// Convert Status to StatusDTO (to prevent exposing full entity)
	        StatusDTO statusDTO = new StatusDTO(status);
			
			String message = updateBookingRequest.isConfirmedByDoctor() ? "Booking accepted successfully." : "Booking canceled successfully.";
			
			return ResponseEntity.ok(new ResponseDTO<>(message, statusDTO, HttpStatus.OK.value(), getCurrentTime()));
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDTO<>(e.getMessage(),null,HttpStatus.BAD_REQUEST.value(), getCurrentTime()));
		}
		
	}
	
}
