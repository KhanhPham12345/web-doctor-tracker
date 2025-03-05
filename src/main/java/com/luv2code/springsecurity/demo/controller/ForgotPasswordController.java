
package com.luv2code.springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.dto.AuthRequest;
import com.luv2code.springsecurity.demo.dto.EmailAuthRequest;
import com.luv2code.springsecurity.demo.dto.ForgotPasswordRequest;
import com.luv2code.springsecurity.demo.dto.ResetPasswordRequest;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

	@Autowired
	private UserService userService;

	// thêm method get để tới trang quên mk, có 1 dòng là email
	@GetMapping("/forgot-password")
	public ResponseEntity<?> showForgotPasswordPage() {
		EmailAuthRequest emailAuthRequest = new EmailAuthRequest("");
		return ResponseEntity.status(HttpStatus.OK).body(emailAuthRequest);
	}

	// Step 1: Confirm email and send reset token
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		try {
			userService.sendPasswordResetToken(request.getEmail());

			return ResponseEntity.ok("Password reset token sent to your email.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}
	
	// Method to show the reset password page
	@GetMapping("/reset-password")
	public ResponseEntity<String> showResetPasswordPage(@RequestParam String token) {
		try {
			userService.validateResetToken(token); // Validate the token
			return ResponseEntity.ok("Token is valid. Please provide a new password.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	// Reset the password without requiring the token in the request
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(token, request.getNewPassword());
            return ResponseEntity.ok("Password has been successfully reset.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
