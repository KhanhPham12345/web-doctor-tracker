package com.luv2code.springsecurity.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springsecurity.demo.entity.MedHistory;

public interface MedhistoryRepository extends JpaRepository<MedHistory, Integer> {

}
