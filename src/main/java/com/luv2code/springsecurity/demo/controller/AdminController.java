package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.dto.DoctorDTO;
import com.luv2code.springsecurity.demo.dto.DoctorRequestDTO;
import com.luv2code.springsecurity.demo.dto.LockAccountRequest;
import com.luv2code.springsecurity.demo.dto.ResponseDTO;
import com.luv2code.springsecurity.demo.dto.StatusDTO;
import com.luv2code.springsecurity.demo.dto.StatusDTOForUser;
import com.luv2code.springsecurity.demo.dto.UpdateBookingRequest;
import com.luv2code.springsecurity.demo.dto.UserDTO;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.entity.Status;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.ClinicService;
import com.luv2code.springsecurity.demo.service.DoctorService;
import com.luv2code.springsecurity.demo.service.StatusService;
import com.luv2code.springsecurity.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
    private DoctorService doctorService;
	
	@Autowired
    private StatusService statusService;

	private String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	private String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        // Safely cast to UserDetails and get the username
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        return userDetails.getUsername();
	    } else {
	        return null;
	    }
	}
	
	@PostMapping("/lock-account/{patientId}")
	public ResponseEntity<ResponseDTO<String>> lockAccount(@PathVariable int patientId, @RequestBody LockAccountRequest request){
		try {
			String adminUsername = getCurrentUsername(); // Get logged-in admin
            if (adminUsername == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDTO<>("Unauthorized access.", null, HttpStatus.UNAUTHORIZED.value(), getCurrentTime()));
            }
			boolean success = userService.lockUserAccount(patientId,request.getDescription());
			if(success) {
				return ResponseEntity.ok(new ResponseDTO<>("Patient account locked success",null,HttpStatus.OK.value(), getCurrentTime()));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>("Failed to lock account.", null, HttpStatus.BAD_REQUEST.value(), getCurrentTime()));
			}
		}catch(Exception e){
			System.out.println("This is the error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value(), getCurrentTime()));
		}
	}
	
	@PostMapping("/unlock-account/{patientId}")
	public ResponseEntity<ResponseDTO<String>> unLockAccount(@PathVariable int patientId, @RequestBody LockAccountRequest request){
		try {
			String adminUsername = getCurrentUsername(); // Get logged-in admin
            if (adminUsername == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDTO<>("Unauthorized access.", null, HttpStatus.UNAUTHORIZED.value(), getCurrentTime()));
            }
			boolean success = userService.unlockUserAccount(patientId,request.getDescription());
			if(success) {
				return ResponseEntity.ok(new ResponseDTO<>("Patient account unlocked success",null,HttpStatus.OK.value(), getCurrentTime()));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>("Failed to unlock account.", null, HttpStatus.BAD_REQUEST.value(), getCurrentTime()));
			}
		}catch(Exception e){
			System.out.println("This is the error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value(), getCurrentTime()));
		}
	}
	
	//output is DoctorDTO
	@PostMapping("/create-doctor")
    public ResponseEntity<ResponseDTO<DoctorDTO>> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {        
        try {
        	User doctor = doctorService.createDoctor(doctorRequestDTO);
        	
            DoctorDTO doctorDTO = doctorService.convertToDoctorDTO(doctor);
        	
        	return ResponseEntity.ok(new ResponseDTO<>("Doctor created successfully",doctorDTO, getCurrentTime()));
        }catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("Error creating doctor: " + e.getMessage(),null,HttpStatus.INTERNAL_SERVER_ERROR.value(),getCurrentTime()));
        }
    }
	
	@DeleteMapping("/delete_user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User(patient) deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
	
	@DeleteMapping("/delete_doc/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable int doctorId) {
        try {
            userService.deleteDoctor(doctorId);
            return ResponseEntity.ok("Doctor deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
	
//	@GetMapping("/all_doctors")
//    public ResponseEntity<List<DoctorDTO>> getDoctors() {
//        List<DoctorDTO> doctors = doctorService.getDoctors();
//        return ResponseEntity.ok(doctors);
//    }
	
	@GetMapping("/all_doctors")
	public ResponseEntity<ResponseDTO<List<DoctorDTO>>> getDoctors() {
	    try {
	        List<DoctorDTO> doctors = doctorService.getDoctors();
	        return ResponseEntity.ok(new ResponseDTO<>("Doctors retrieved successfully", 
	                            doctors != null ? doctors : Collections.emptyList(), getCurrentTime()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ResponseDTO<>("Error fetching doctors: " + e.getMessage(), 
	                            null, 500, getCurrentTime()));
	    }
	}
	
	
//	@GetMapping("/all_users")
//    public ResponseEntity<List<UserDTO>> getUsers() {
//        List<UserDTO> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
	
	@GetMapping("/all_users")
	public ResponseEntity<ResponseDTO<List<UserDTO>>> getUsers() {
	    try {
	        List<UserDTO> users = userService.getAllUsers();
	        return ResponseEntity.ok(new ResponseDTO<>("Users retrieved successfully", 
	                            users != null ? users : Collections.emptyList(), getCurrentTime()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ResponseDTO<>("Error fetching users: " + e.getMessage(), 
	                            null, 500, getCurrentTime()));
	    }
	}
	
	@GetMapping("/patient_statuses/{userId}")
    public ResponseEntity<List<StatusDTO>> getStatusesFromUser(@PathVariable int userId) {
        try {
        	User user = userService.findById(userId);
        	List<Status> userStatuses = user.getStatus();
        	List<StatusDTO> statuses = new ArrayList<>();
        	for(Status status : userStatuses) {
        		StatusDTO newStatus = new StatusDTO(status);
        		statuses.add(newStatus);
        	}
        	return ResponseEntity.ok(statuses);
        }catch(Exception e){
        	System.out.println("Error: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Collections.emptyList());
        }
    }
	
	
	
	@GetMapping("/doctor_statuses/{doctorId}")
	public ResponseEntity<List<StatusDTOForUser>> getStatusesFromDoctor(@PathVariable int doctorId) {
	    try {
	    	System.out.println("DoctorId="+doctorId);
	        // Retrieve the list of statuses for the given doctor
	        List<Status> statuses = statusService.getStatusesByDoctorId(doctorId);
	        
	        System.out.println("All statuses: "+ statuses);
	        // Convert the list of Status to StatusDTOForUser
	     // Convert the list of Status to StatusDTOForUser using a for loop
	        List<StatusDTOForUser> statusDTOList = new ArrayList<>();
	        for (Status status : statuses) {
	            StatusDTOForUser statusDTO = new StatusDTOForUser(status);
	            statusDTOList.add(statusDTO);
	        }
	        
	        return ResponseEntity.ok(statusDTOList);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	        // Handle any exception (e.g., doctor not found)
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Collections.emptyList());
	    }
	}
}
