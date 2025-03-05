package com.luv2code.springsecurity.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springsecurity.demo.dto.BookingRequest;
import com.luv2code.springsecurity.demo.dto.ResponseDTO;
import com.luv2code.springsecurity.demo.dto.UserDTO;
import com.luv2code.springsecurity.demo.dto.UserProfileUpdateDTO;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.BookingService;
import com.luv2code.springsecurity.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BookingService bookingService;
	
	private String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO<User>> registerUser(@RequestBody User user, @RequestParam String roleName){
		try {
			User registeredUser = userService.registerUser(user, roleName);
			ResponseDTO<User> response = new ResponseDTO<>(
					"User registered successfully",
					registeredUser,
					0,
					getCurrentTime()
			);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(RuntimeException e) {
			ResponseDTO<User>response = new ResponseDTO<>(
					e.getMessage(),
					null,
					HttpStatus.BAD_REQUEST.value(),
					getCurrentTime()
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	
	@GetMapping("/profile")
	public ResponseEntity<ResponseDTO<UserDTO>>getUserProfile(@RequestHeader("Authorization") String authorizationHeader){
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	    	ResponseDTO<UserDTO> response = new ResponseDTO<>(
    			"Authorization header is missing or invalid",
    			null,
    			HttpStatus.BAD_REQUEST.value(),
    			getCurrentTime()
	    	);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	    
	    String token = authorizationHeader.substring(7);
	    
	    try {
	        String username = userService.getUsernameFromToken(token);
	        UserDTO userDTO = userService.getPersonalInformation(username);
	        User user = userService.findByEmail(username);
	        
	        // Check if the account is locked
	        if (user.getIsActive() == 0) {
	            ResponseDTO<UserDTO> response = new ResponseDTO<>(
	                "Account is locked. You cannot access the profile.",
	                null,
	                HttpStatus.FORBIDDEN.value(),
	                getCurrentTime()
	            );
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	        }
	        
	        // Fetch user profile if account is not locked
	        ResponseDTO<UserDTO> response = new ResponseDTO<>(
	        	"User profile retrieved successfully",
	        	userDTO,
	        	0,
	        	getCurrentTime()
	        );
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	    	ResponseDTO<UserDTO> response = new ResponseDTO<>(
	    		e.getMessage(),
	    		null,
	    		HttpStatus.UNAUTHORIZED.value(),
	    		getCurrentTime()	
	    	);
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDTO<UserDTO>> updateUserInfo(@RequestHeader("Authorization") String authorizationHeader,@RequestBody UserProfileUpdateDTO userUpdateDTO) {
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			ResponseDTO<UserDTO> response = new ResponseDTO<>(
					"Authorization header is missing or invalid",
					null,
					HttpStatus.BAD_REQUEST.value(),
					getCurrentTime()
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		String token = authorizationHeader.substring(7);
		
		try {
			String username = userService.getUsernameFromToken(token);
			User currentUser = userService.findByEmail(username);
			
			// Check if the account is locked
	        if (currentUser.getIsActive() == 0) {
	            ResponseDTO<UserDTO> response = new ResponseDTO<>(
	                "Account is locked. You cannot access the profile.",
	                null,
	                HttpStatus.FORBIDDEN.value(),
	                getCurrentTime()
	            );
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	        }
	        
	        User updatedUser = userService.updateUserInfo(username, userUpdateDTO);
	        UserDTO userDTO = new UserDTO(updatedUser);
	        
	        // Proceed with updating user info if the account is not locked
			ResponseDTO<UserDTO> response = new ResponseDTO<>(
				"User information updated successfully",
				userDTO,
				0,
				getCurrentTime()
			);
			return ResponseEntity.ok(response);
		}catch(RuntimeException e) {
			ResponseDTO<UserDTO> response = new ResponseDTO<>(
				e.getMessage(),
				null,
				HttpStatus.BAD_REQUEST.value(),
				getCurrentTime()
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		
	}
	
	@PostMapping("/create_session")
    public ResponseEntity<ResponseDTO<?>> bookAppointment(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BookingRequest bookingRequest) {
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			ResponseDTO<?> response = new ResponseDTO<>(
					"Authorization header is missing or invalid",
					null,
					HttpStatus.BAD_REQUEST.value(),
					getCurrentTime()
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		String token = authorizationHeader.substring(7);
	
        try {
        	
        	String username = userService.getUsernameFromToken(token);
        	User patient = userService.findByEmail(username);
        	
        	// Check if the account is locked
            if (patient.getIsActive() == 0) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>(
                    "Account is locked. You cannot access the profile.",
                    null,
                    HttpStatus.FORBIDDEN.value(),
                    getCurrentTime()
                );
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        	
         	int patientId = patient.getId();
         	
         	System.out.println("Current user id: "+ patientId);
         	
            String message = bookingService.bookAppointment(
                bookingRequest.getDoctorName(),
                bookingRequest.getTimeSlot(),
                bookingRequest.getConsultationFee(),
                bookingRequest.getPatientName(),
                bookingRequest.getGender(),
                bookingRequest.getPhoneNumber(),
                bookingRequest.getDob(),
                bookingRequest.getAddress(),
                bookingRequest.getReason(),
                bookingRequest.getClinicName(),
                patientId
            );

            return ResponseEntity.ok(new ResponseDTO<>(message, null, 0, LocalDateTime.now().toString()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(e.getMessage(), null, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString()));
        }
    }
	
}
