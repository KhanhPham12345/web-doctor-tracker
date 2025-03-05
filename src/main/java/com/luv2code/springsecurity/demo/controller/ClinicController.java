package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.dto.ClinicDTO;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/top")
    public ResponseEntity<?> getTopClinics() {
        try {
            List<ClinicDTO> topClinics = clinicService.getTopClinics();
            return ResponseEntity.ok(topClinics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error: " + e.getMessage());
        }
    }
}
