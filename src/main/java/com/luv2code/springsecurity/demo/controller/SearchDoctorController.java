package com.luv2code.springsecurity.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springsecurity.demo.dto.DoctorDTO;
import com.luv2code.springsecurity.demo.dto.ResponseDTO;
import com.luv2code.springsecurity.demo.dto.SearchRequest;
import com.luv2code.springsecurity.demo.service.DoctorService;

@RestController
@RequestMapping("/api/search")
public class SearchDoctorController {
	@Autowired
	private DoctorService doctorService;
	
//	@GetMapping("/all_doctors")
//    public ResponseEntity<List<DoctorDTO>> getDoctors() {
//        List<DoctorDTO> doctors = doctorService.getDoctors();
//        return ResponseEntity.ok(doctors);
//    }
	
	private String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
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
		
//	@GetMapping("/doctor")
//	public ResponseEntity<List<DoctorDTO>> searchDoctors(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Integer consultFee) {
//		
//		List<DoctorDTO> doctors;
//		
//		if (keyword == null && consultFee == null) {
//	        // Fetch all doctors if no filter is provided
//	        doctors = doctorService.getDoctors();
//	    } else {
//	        // Apply search filters if at least one parameter is provided
//	        doctors = doctorService.searchDoctors(keyword, consultFee);
//	    }
//   
//        return new ResponseEntity<>(doctors, HttpStatus.OK);
//    }
	
	
	@GetMapping("/doctor")
    public ResponseEntity<ResponseDTO<List<DoctorDTO>>> searchDoctors(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer consultFee) {
        
        try {
            List<DoctorDTO> doctors;
            if (keyword == null && consultFee == null) {
                doctors = doctorService.getDoctors();
            } else {
                doctors = doctorService.searchDoctors(keyword, consultFee);
            }
            return ResponseEntity.ok(new ResponseDTO<>(
                    "Doctors retrieved successfully",
                    doctors != null ? doctors : Collections.emptyList(),
                    getCurrentTime()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(
                            "Error fetching doctors: " + e.getMessage(),
                            Collections.emptyList(),
                            500,
                            getCurrentTime()
                    ));
        }
    }

	
}
