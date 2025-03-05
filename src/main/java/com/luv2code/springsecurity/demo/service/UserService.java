package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.dto.CrmUser;
import com.luv2code.springsecurity.demo.dto.UpdateUserDTO;
import com.luv2code.springsecurity.demo.dto.UserDTO;
import com.luv2code.springsecurity.demo.dto.UserProfileUpdateDTO;
import com.luv2code.springsecurity.demo.entity.User;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
	public User findByUserName(String userName);
	public void save(CrmUser crmUser);
	public User registerUser(User user, String roleName);
	public void sendPasswordResetToken(String email);
	public void resetPassword(String token, String newPassword);
	public void validateResetToken(String token);
	public UserDTO getPersonalInformation(String username);
	public void updatePersonalInformation(String username, UpdateUserDTO updateUserDTO);
	public String getUsernameFromToken(String token);
	public User updateUserInfo(String username, UserProfileUpdateDTO userUpdateDTO);
	public User findByEmail(String username);
	public User findById(int theId);
	public List<User> getPatientsByDoctorId(int doctorId);
	public boolean lockUserAccount(int patientId, String description);
	public boolean unlockUserAccount(int patientId, String description);
	public String deleteDoctor(int doctorId);
	public String deleteUser(int doctorId);
	public List<String> getPatientEmailsByDoctor(int doctorId);
	public List<UserDTO> getAllUsers();
}
