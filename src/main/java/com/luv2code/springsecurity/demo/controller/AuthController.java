package com.luv2code.springsecurity.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springsecurity.demo.config.JwtUtil;
import com.luv2code.springsecurity.demo.dto.AuthRequest;
import com.luv2code.springsecurity.demo.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/login")
	public ResponseEntity<?> loginForm() {
		AuthRequest authRequest = new AuthRequest("", "");
		return ResponseEntity.status(HttpStatus.OK).body(authRequest);
	}

	// Endpoint to authenticate user and generate JWT token
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
		try {
			// Extract email and password from request body
			String email = credentials.get("email");
			String password = credentials.get("password");

			// Authenticate user using AuthenticationManager
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));

			// Set authentication in SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Generate JWT token
			String token = jwtUtil.generateToken(email);

			// Return the token in the response
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		} catch (BadCredentialsException e) {
			// Handle invalid credentials
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
		} catch (Exception e) {
			// Handle general errors
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during login: " + e.getMessage());
		}
	}
}
