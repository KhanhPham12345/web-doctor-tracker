package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.Clinic;
import com.luv2code.springsecurity.demo.dao.ClinicRepository;
import com.luv2code.springsecurity.demo.dto.ClinicDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<ClinicDTO> getTopClinics() {
        return clinicRepository.findTopClinicsByDoctorCount();
    }
}
