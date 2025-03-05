package com.luv2code.springsecurity.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luv2code.springsecurity.demo.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	@Query(value = "SELECT s.* FROM schedules s JOIN users u ON s.doctor_id = u.id WHERE u.name = :doctorName AND s.time = :timeSlot", nativeQuery = true)
	Optional<Schedule> findByDoctorNameAndTime(@Param("doctorName") String doctorName, @Param("timeSlot") String timeSlot);
}
