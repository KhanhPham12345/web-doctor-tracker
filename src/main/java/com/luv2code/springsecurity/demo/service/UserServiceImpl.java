package com.luv2code.springsecurity.demo.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.config.JwtUtil;
import com.luv2code.springsecurity.demo.dao.PlaceRepository;
import com.luv2code.springsecurity.demo.dao.RoleRepository;
import com.luv2code.springsecurity.demo.dao.UserRepository;
import com.luv2code.springsecurity.demo.dao.WorkExperienceRepository;
import com.luv2code.springsecurity.demo.dto.CrmUser;
import com.luv2code.springsecurity.demo.dto.UpdateUserDTO;
import com.luv2code.springsecurity.demo.dto.UserDTO;
import com.luv2code.springsecurity.demo.dto.UserProfileUpdateDTO;
import com.luv2code.springsecurity.demo.entity.Place;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
    	
    	if (crmUser == null) {
            throw new IllegalArgumentException("CrmUser cannot be null");
        }
    	
        User user = new User();
        // Assign user details to the user object
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setName(crmUser.getFullName());
        user.setEmail(crmUser.getEmail());
        user.setAddress(crmUser.getAddress());
        user.setGender(crmUser.getGender());
        // Assign default role
        Optional<Role> result = roleRepository.findById(crmUser.getRole());
        
        Role theRole = null;
		
		if (result.isPresent()) {
			theRole = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + crmUser.getRole());
		}
        user.setRole(theRole);

        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public User registerUser(User user, String roleName) {
        // Check if the user already exists by username or email
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new RuntimeException("Username is already taken.");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email is already registered.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Find the role
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found.");
        }
        
        // Set the role and save the user
        user.setRole(role);
        user.setIsActive(1); // Default to active
        user.setPlace(findDefaultPlace(1));
        user.setFee(0);
        return userRepository.save(user);
    }
    
    public Place findDefaultPlace(int id) {
    	Optional<Place> place = placeRepository.findById(id);
        Place placeDefault = null;
        if(place.isPresent()) {
        	placeDefault = place.get();
        }else {
        	throw new RuntimeException("Did not find place id - " + 1);
        }
		return placeDefault;
    }
    
    @Override
    @Transactional
    public void sendPasswordResetToken(String email) {
    	
    	// Step 1: Check if the user exists
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found.");
        }

        // Step 2: Generate a JWT reset token
        String token = UUID.randomUUID().toString();
        // sử dụng uuid random
        
        user.setResetToken(token);
        userRepository.save(user);
        
        // Step 3: Send email with the reset token
     
        try {
        	String toEmail = email;
        	String subject = "vui lòng truy cập link bên dưới để đổi mật khẩu";
        	String link = "http://localhost:8080/api/auth/reset-password?token=" + token;
        	emailService.sendEmail(toEmail,subject, link);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send reset token email.", e);
        }

        // Log the action for debugging purposes (you may replace this with actual logging)
        System.out.println("Reset token sent to: " + email);
    	
    }
    
    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // Step 1: Validate the token
    	User user = userRepository.findByResetToken(token);
        if (user==null) {
            throw new IllegalArgumentException("User with token " + token + " not found.");
        }

        // Step 2: Update the user's password
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetToken(null); // Xóa token sau khi sử dụng
        
        // Save the updated user record in the database
        userRepository.save(user);

        // Log the password reset action
        System.out.println("Password reset successfully for user: " + token);
    }

	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

	@Override
	@Transactional
	public void validateResetToken(String token) {
		User user = userRepository.findByResetToken(token);
        if (user==null) {
            throw new IllegalArgumentException("User with token " + token + " not found.");
        }
	}
	
	@Override
	@Transactional
	public String getUsernameFromToken(String token) {
	    try {
	        return jwtUtil.extractUsername(token);
	    } catch (Exception e) {
	        throw new RuntimeException("Invalid token");
	    }
	}
	
	@Override
	@Transactional
    public UserDTO getPersonalInformation(String username) {
        User user = userRepository.findByEmail(username);
        if (user==null) {
            throw new IllegalArgumentException("User with usernam " + username + " not found.");
        }
        return new UserDTO(user);
    }
	
	@Override
	@Transactional
    public void updatePersonalInformation(String username, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByEmail(username);
        if (user==null) {
            throw new IllegalArgumentException("User with username " + username + " not found.");
        }
        user.setName(updateUserDTO.getName());
        user.setAddress(updateUserDTO.getAddress());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        user.setGender(updateUserDTO.getGender());

        userRepository.save(user);
    }
	
	@Override
	@Transactional
	public User updateUserInfo(String username, UserProfileUpdateDTO userUpdateDTO) {
		User user = userRepository.findByEmail(username);
		
		if(user == null) {
			throw new RuntimeException("User not found");
		}
		
		if(userUpdateDTO.getName() != null) {
			user.setName(userUpdateDTO.getName());
		}
		
		if(userUpdateDTO.getPhone() != null) {
			user.setPhoneNumber(userUpdateDTO.getPhone());
		}
		
		if(userUpdateDTO.getAddress() != null) {
			user.setAddress(userUpdateDTO.getAddress());
		}
		
		if(userUpdateDTO.getAvatar() != null) {
			user.setAvatar(userUpdateDTO.getAvatar());
		}
		
		if (userUpdateDTO.getGender() != null) {
            user.setGender(userUpdateDTO.getGender());
        }
		
		if (userUpdateDTO.getDescription() != null) {
            user.setDescription(userUpdateDTO.getDescription());
        }
        // Automatically set the updated_at field to the current timestamp
        user.setUpdatedAt(LocalDateTime.now());
		
		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	@Override
	public User findById(int theId) {
		Optional<User> result = userRepository.findById(theId);
		User theUser = null;
		if (result.isPresent()) {
			theUser = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find user with given id - " + theId);
		}
		return theUser;
	}
	
	
	@Override
	public List<User> getPatientsByDoctorId(int doctorId) {
        return userRepository.findPatientsByDoctorId(doctorId);
    }

	@Override
	public boolean lockUserAccount(int patientId, String description) {
		try {
	        System.out.println("Locking user account ID: " + patientId + " with reason: " + description);
	        User user = userRepository.findById(patientId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        user.setIsActive(0);
	        user.setNote(description);
	        userRepository.save(user);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean unlockUserAccount(int patientId, String description) {
		try {
	        System.out.println("Unlocking user account ID: " + patientId + " with reason: " + description);
	        User user = userRepository.findById(patientId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        user.setIsActive(1);
	        user.setNote(description);
	        userRepository.save(user);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	@Transactional
	public String deleteUser(int userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Doctor not found"));
		
		System.out.println("current user id: "+ user.getId());
		if(!user.getRole().getName().equals("ROLE_USER")) {
			throw new RuntimeException("User is not a user");
		}
		
		// Then delete the doctor
        userRepository.deleteById(userId);

		return "User account deleted successfully.";
	}
	
	@Override
	@Transactional
	public String deleteDoctor(int doctorId) {
		User doctor = userRepository.findById(doctorId).orElseThrow(()-> new RuntimeException("Doctor not found"));
		
		System.out.println("current doctor id: "+ doctor.getId());
		if(!doctor.getRole().getName().equals("ROLE_DOCTOR")) {
			throw new RuntimeException("User is not a doctor");
		}
		
		// Then delete the doctor
        userRepository.deleteById(doctorId);

		return "Doctor account deleted successfully.";
	}

	@Override
	public List<String> getPatientEmailsByDoctor(int doctorId) {
		return userRepository.findPatientEmailsByDoctorInt(doctorId);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> allUsers = new ArrayList<>();
		for(User user: users) {
			if("ROLE_USER".equals(user.getRole().getName())) {
				UserDTO newUser = new UserDTO(user);
				allUsers.add(newUser);
			}
		}
		return allUsers;
	}
	
	
}
