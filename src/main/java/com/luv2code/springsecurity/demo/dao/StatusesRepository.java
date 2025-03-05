package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springsecurity.demo.entity.Status;

public interface StatusesRepository extends JpaRepository<Status, Integer> {
	
	List<Status> findByDoctorId(int doctorId);
}
