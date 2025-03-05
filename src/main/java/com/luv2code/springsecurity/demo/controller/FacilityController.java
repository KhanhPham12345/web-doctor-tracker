package com.luv2code.springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springsecurity.demo.dto.ClinicWithFacilitiesDTO;
import com.luv2code.springsecurity.demo.dto.FacilitySearchRequest;
import com.luv2code.springsecurity.demo.dto.ResponseDTO;
import com.luv2code.springsecurity.demo.service.FacilityService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

//    @PostMapping("/search/facility")
//    public Map<String, Object> searchFacilities(@RequestBody FacilitySearchRequest request) {
//        List<ClinicWithFacilitiesDTO> data = facilityService.searchFacilities(
//                request.getCountry(),
//                request.getRegionState(),
//                request.getCity(),
//                request.getHospitalName()
//        );
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("data", data);
//        return response;
//    }
    
    @PostMapping("/search/facility")
    public ResponseEntity<ResponseDTO<List<ClinicWithFacilitiesDTO>>> searchFacilities(@RequestBody FacilitySearchRequest request) {
        try {
            List<ClinicWithFacilitiesDTO> data = facilityService.searchFacilities(
                    request.getCountry(),
                    request.getRegionState(),
                    request.getCity(),
                    request.getHospitalName()
            );

            return ResponseEntity.ok(new ResponseDTO<>("Facilities fetched successfully", data, getCurrentTime()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>("Error fetching facilities: " + e.getMessage(), null, 500, getCurrentTime()));
        }
    }
    
    
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}

