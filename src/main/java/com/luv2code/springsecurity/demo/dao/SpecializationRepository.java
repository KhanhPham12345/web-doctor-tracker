package com.luv2code.springsecurity.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.dto.SpecializationDTO;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

	@Query(value = "SELECT s.id AS id, s.name AS name, s.description AS description, s.image AS image, s.created_at AS createdAt, s.updated_at AS updatedAt, COUNT(du.id) AS doctorCount FROM specializations s LEFT JOIN doctor_specializations du ON s.id = du.specialization_id GROUP BY s.id ORDER BY doctorCount DESC", nativeQuery = true)
	List<SpecializationDTO> findTopSpecializations();

	@Query(value = "SELECT s.name FROM specializations s "
			+ "JOIN doctor_specializations du ON s.id = du.specialization_id "
			+ "WHERE du.doctor_id = :doctorId", nativeQuery = true)
	List<String> findSpecializationNamesByDoctorId(@Param("doctorId") int i);

	@Query(value = "SELECT * FROM specializations s WHERE s.name = :specializationName", nativeQuery = true)
	Specialization findByName(@Param("specializationName") String specializationName);
}
