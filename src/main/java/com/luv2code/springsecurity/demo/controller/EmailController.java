package com.luv2code.springsecurity.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luv2code.springsecurity.demo.config.JwtUtil;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.EmailService;
import com.luv2code.springsecurity.demo.service.UserService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;
    @Autowired
	private JwtUtil jwtUtil;

    @PostMapping("/doctor_send")
    public String sendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam MultipartFile file,
            @RequestHeader("Authorization") String authHeader) {
    	
    	// Extract JWT token from "Bearer <token>"
        String token = authHeader.replace("Bearer ", "");

        // Extract doctor email from JWT token
        String doctorEmail = jwtUtil.extractUsername(token);
        
        System.out.println("Current doctor Email:"+doctorEmail);
        
        //Get doctor id

        int docId = userService.findByEmail(doctorEmail).getId();


        // Fetch the list of registered patients for this doctor
        List<String> patientEmails = userService.getPatientEmailsByDoctor(docId);
        System.out.println("All patients"+patientEmails);
        
        // Check if the recipient email is in the list
        if (!patientEmails.contains(to)) {
            return "Access Denied: The recipient is not a registered patient of this doctor.";
        }
    	
        try {
            emailService.sendEmailWithAttachment(to, subject, text, file);
            return "Email sent successfully to " + to;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
