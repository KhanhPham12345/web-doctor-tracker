package com.luv2code.springsecurity.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luv2code.springsecurity.demo.dto.ClinicDTO;
import com.luv2code.springsecurity.demo.entity.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

	@Query(value = "SELECT c.*, COUNT(du.id) AS doctorCount FROM clinics c " + "JOIN doctor_users du ON c.id = du.clinic_id " + "GROUP BY c.id "
			+ "ORDER BY COUNT(du.doctor_id) DESC " + "LIMIT 5", nativeQuery = true)
	List<ClinicDTO> findTopClinicsByDoctorCount();

	@Query(value = "SELECT c.* FROM clinics c " + "JOIN doctor_users du ON du.clinic_id = c.id "
			+ "WHERE du.doctor_id = :doctorId " + "AND c.name = :clinicName", nativeQuery = true)
	Optional<Clinic> findClinicByDoctorIdAndClinic(@Param("doctorId") int doctorId,
			@Param("clinicName") String clinicName);

	@Query(value = "SELECT * FROM clinics c WHERE c.name = :clinicName", nativeQuery = true)
	Clinic findByName(@Param("clinicName")String clinicName);
}
