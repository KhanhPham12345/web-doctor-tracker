package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.dto.DoctorDTO;
import com.luv2code.springsecurity.demo.dto.DoctorRequestDTO;
import com.luv2code.springsecurity.demo.dto.SearchRequest;
import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.entity.Place;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.Specialization;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.entity.WorkExperience;
import com.luv2code.springsecurity.demo.dao.ClinicRepository;
import com.luv2code.springsecurity.demo.dao.PlaceRepository;
import com.luv2code.springsecurity.demo.dao.RoleRepository;
import com.luv2code.springsecurity.demo.dao.SpecializationRepository;
import com.luv2code.springsecurity.demo.dao.UserRepository;
import com.luv2code.springsecurity.demo.dao.WorkExperienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DoctorService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkExperienceRepository workExperienceRepository;

	@Autowired
	private SpecializationRepository specializationRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ClinicRepository clinicRepository;
	
	@Autowired
	private PlaceRepository placeRepository;

	public List<DoctorDTO> getDoctors() {
		List<User> users = userRepository.findAll();
		List<DoctorDTO> doctorDTOs = new ArrayList<>();

		for (User user : users) {
			if (user.getRole() != null && user.getRole().getId() == 2) {
				DoctorDTO doctorDTO = new DoctorDTO();
				doctorDTO.setDoctorId(user.getId());
				doctorDTO.setFullname(user.getName());
				doctorDTO.setGender(user.getGender());
				doctorDTO.setProfileImageUrl(user.getAvatar());
				doctorDTO.setSpecialties(getSpecialtiesForDoctor(user.getId()));
				doctorDTO.setWorkExperiences(getWorkExperiencesForDoctor(user.getId()));
				doctorDTO.setConsultFee(getConsultFeeForDoctor(user.getId()));
				doctorDTO.setBackground(getBackgroundForDoctor(user.getId()));
				doctorDTO.setPracticeLocations(getPlaceForDoctor(user.getId()));
				doctorDTOs.add(doctorDTO);
			}
		}

		return doctorDTOs;
	}

	private List<String> getSpecialtiesForDoctor(int i) {
		return specializationRepository.findSpecializationNamesByDoctorId(i);
	}
	
	private int getConsultFeeForDoctor(int i) {
		return userRepository.findConsultFee(i);
	}
	
	private String getBackgroundForDoctor(int i) {
		return userRepository.findBackground(i);
	}
	
	private String getPlaceForDoctor(int i) {
		return userRepository.findWorkplace(i);
	}

	private List<String> getWorkExperiencesForDoctor(int i) {
		return workExperienceRepository.findWorkExperienceDescriptionsByDoctorId(i);
	}
	
	public DoctorDTO convertToDoctorDTO(User user) {
		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setDoctorId(user.getId());
		doctorDTO.setFullname(user.getName());
		doctorDTO.setGender(user.getGender());
		doctorDTO.setProfileImageUrl(user.getAvatar());
		doctorDTO.setSpecialties(getSpecialtiesForDoctor(user.getId()));
		doctorDTO.setWorkExperiences(getWorkExperiencesForDoctor(user.getId()));
		doctorDTO.setConsultFee(user.getFee());
		doctorDTO.setBackground(user.getBackground());
		doctorDTO.setPracticeLocations(user.getPlace().getName());
		
	    return doctorDTO;
	}
	
	
	public List<DoctorDTO> searchDoctors(String keyword,Integer consultedFee) {

		List<Object[]> results = userRepository.searchDoctors(keyword,consultedFee);
		
		List<DoctorDTO> doctorDTOs = new ArrayList<>();

		for (Object[] row : results) {
			DoctorDTO doctorDTO = new DoctorDTO();
			doctorDTO.setDoctorId((Integer) row[0]);
			doctorDTO.setPracticeLocations((String) row[1]);
			doctorDTO.setFullname((String) row[2]);
			doctorDTO.setGender((String) row[3]);
			doctorDTO.setProfileImageUrl((String) row[4]);
			doctorDTO.setSpecialties(row[5] != null ? Arrays.asList(((String) row[5]).split(", ")) : new ArrayList<>());
			doctorDTO.setConsultFee(row[6] != null ? (Integer) row[6] : null);
			doctorDTO.setWorkExperiences(
					row[7] != null ? Arrays.asList(((String) row[7]).split("; ")) : new ArrayList<>());
			doctorDTO.setBackground((String) row[8]);

			doctorDTOs.add(doctorDTO);
		}

		return doctorDTOs;
	}

	public User createDoctor(DoctorRequestDTO doctorRequestDTO) {
		// Create and set User (Doctor)
		User doctor = new User();
		
		doctor.setUserName(doctorRequestDTO.getUsername());
		doctor.setPassword(passwordEncoder.encode(doctorRequestDTO.getPassword()));
		doctor.setName(doctorRequestDTO.getName());
		doctor.setAddress(doctorRequestDTO.getAddress());
		doctor.setPhoneNumber(doctorRequestDTO.getPhoneNumber());
		doctor.setAvatar(doctorRequestDTO.getAvatar());
		doctor.setGender(doctorRequestDTO.getGender());
		doctor.setEmail(doctorRequestDTO.getEmail());
		doctor.setDescription(doctorRequestDTO.getDescription());
		doctor.setIsActive(1); // Set doctor as active
		doctor.setBackground(doctorRequestDTO.getBackground());
		doctor.setFee(doctorRequestDTO.getConsultfee());
		
		// Assign ROLE_DOCTOR
		Role doctorRole = roleRepository.findByName("ROLE_DOCTOR");
		if (doctorRole == null) {
			throw new RuntimeException("Role 'ROLE_DOCTOR' not found");
		}
		doctor.setRole(doctorRole);
		
		
		Place place = placeRepository.findByName(doctorRequestDTO.getPlace());
	    if (place == null) {
	        // If the place does not exist, insert it into the places table
	        place = new Place();
	        place.setName(doctorRequestDTO.getPlace());
	        placeRepository.save(place); // Save new place and get the id
	    }
		
	    doctor.setPlace(place);
	    
//		 Handle Specializations (Check if exists, create if not)

		List<Specialization> allSpecializations = new ArrayList<>();

		for (String specializationName : doctorRequestDTO.getSpecializations()) {
			Specialization specialization = specializationRepository.findByName(specializationName);
			if (specialization == null) {
				specialization = new Specialization(specializationName);
				specializationRepository.save(specialization);
			}
			allSpecializations.add(specialization);
		}
		doctor.setSpecializations(allSpecializations);
		
		// Save doctor first to get an ID
	    doctor = userRepository.save(doctor);

		// Link & create new Work Experiences

	    List<WorkExperience> workExperiences = new ArrayList<>();

	    for (String workexpName : doctorRequestDTO.getWorkExperienceDescriptions()) {
	        WorkExperience exp = new WorkExperience();
	        exp.setDescription(workexpName);
	        exp.setDoctors(doctor); // Link work experience to the doctor
	        workExperiences.add(exp);
	    }

		doctor.setWorkExperiences(workExperiences);
		
		List<Clinic> clinics = new ArrayList<>();
		for (String clinicName : doctorRequestDTO.getClinic()) {
			Clinic clinic = clinicRepository.findByName(clinicName);
			if (clinic == null) {
				clinic = new Clinic();
				clinic.setName(clinicName);
				clinicRepository.save(clinic);
			}
			clinics.add(clinic);
		}
		doctor.setClinics(clinics);
		
		
		
		userRepository.save(doctor);
		
		return doctor;
	}
}
