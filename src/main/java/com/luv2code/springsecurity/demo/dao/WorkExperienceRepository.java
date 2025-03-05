package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.WorkExperience;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {

	@Query(value = "SELECT we.id FROM doctors_workexp dwe JOIN work_experience we ON dwe.workexp_id = we.id WHERE dwe.doctor_id = :doctorId", nativeQuery = true)
	List<Integer> findWorkExpIdsByDoctorId(@Param("doctorId") int doctorId);

	@Query(value = "SELECT we.description FROM work_experience we "
			+ "WHERE we.doctor_id = :doctorId", nativeQuery = true)
	List<String> findWorkExperienceDescriptionsByDoctorId(@Param("doctorId") int i);
	
	@Query(value = "SELECT * FROM work_experience we WHERE we.description = :description", nativeQuery = true)
	WorkExperience findByName(@Param("description") String workexpName);

    @Query(value = "DELETE FROM work_experience WHERE doctor_id = :doctorId", nativeQuery = true)
	void deleteByDoctorId(@Param("doctorId")int doctorId);
}