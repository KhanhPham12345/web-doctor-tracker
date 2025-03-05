package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.dao.SpecializationRepository;
import com.luv2code.springsecurity.demo.dto.SpecializationDTO;

@Service
public class SpecializationService {
	@Autowired
	private SpecializationRepository specializationRepository;
	
	
    public List<SpecializationDTO> getTopSpecializations() {
        return specializationRepository.findTopSpecializations();
    }
}
