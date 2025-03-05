package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);

	User findByEmail(String email);

	User findByResetToken(String token);

	List<User> findByRoleId(int roleId);

	@Query(value = "SELECT " +
            "    u.id AS doctorId, " +
            "    p.name AS workplace, " +
            "    u.name AS fullName, " +
            "    u.gender AS gender, " +
            "    u.avatar AS profileImageUrl, " +
            "    GROUP_CONCAT(DISTINCT s.name SEPARATOR ', ') AS specialties, " +
            "    u.consult_fee AS consultFee, " +
            "    GROUP_CONCAT(DISTINCT we.description SEPARATOR '; ') AS workExperience, " +
            "    u.background AS background " +
            "FROM users u " +
            "LEFT JOIN places p ON u.place_id = p.id " +
            "LEFT JOIN doctor_users du ON u.id = du.doctor_id " +
            "LEFT JOIN doctor_specializations ds ON u.id = ds.doctor_id " +
            "LEFT JOIN clinics c ON du.clinic_id = c.id " +
            "LEFT JOIN specializations s ON ds.specialization_id = s.id " +
            "LEFT JOIN work_experience we ON u.id = we.doctor_id " +  // Updated this line
            "WHERE u.role_id = 2 " + // Ensures only doctors are included
            "AND (:keyword IS NULL OR p.name LIKE %:keyword% OR u.name LIKE %:keyword% OR c.name LIKE %:keyword% OR s.name LIKE %:keyword%) " +
            "AND (:consultFee IS NULL OR u.consult_fee <= :consultFee) "+
            "GROUP BY u.id, p.name, u.consult_fee, u.background",
            nativeQuery = true)
	List<Object[]> searchDoctors(
	        @Param("keyword") String keyword, @Param("consultFee") Integer consultFee
    );
	
	@Query(value = "SELECT DISTINCT u.* FROM users u JOIN statuses s ON u.id = s.patient_id WHERE s.doctor_id = ?1", nativeQuery = true)
    List<User> findPatientsByDoctorId(int doctorId);
	
	@Query(value = "SELECT u.consult_fee FROM users u WHERE u.id = ?1", nativeQuery = true)
	int findConsultFee(int doctorId);
	
	@Query(value = "SELECT u.background FROM users u WHERE u.id = ?1", nativeQuery = true)
	String findBackground(int doctorId);
	
	@Query(value = "SELECT p.name FROM places p JOIN users u ON u.place_id = p.id WHERE u.id = ?1", nativeQuery = true)
	String findWorkplace(int doctorId);
	
	@Query(value="SELECT p.email FROM users p JOIN statuses dp ON p.id = dp.patient_id WHERE dp.doctor_id = ?1", nativeQuery = true)
    List<String> findPatientEmailsByDoctorInt(int doctorId);
	
}
