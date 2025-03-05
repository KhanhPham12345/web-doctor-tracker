package com.luv2code.springsecurity.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Facility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    @Query(value = "SELECT " +
            "    c.id AS clinicId, " +
            "    c.name AS clinicName, " +
            "    f.facilityId AS facilityId, " +
            "    f.facilityName AS facilityName, " +
            "    f.unitNo AS unitNo, " +
            "    f.block AS block, " +
            "    f.street AS street, " +
            "    f.buildingName AS buildingName, " +
            "    f.regionState AS regionState, " +
            "    f.city AS city, " +
            "    f.country AS country, " +
            "    f.postalCode AS postalCode, " +
            "    f.totalDoctor AS totalDoctor, " +
            "    f.totalSpecialty AS totalSpecialty, " +
            "    f.totalSubSpecialty AS totalSubSpecialty " +
            "FROM facilities f " +
            "LEFT JOIN clinics c ON f.clinic_id = c.id " +
            "WHERE (:country IS NULL OR f.country LIKE %:country%) " +
            "AND (:regionState IS NULL OR f.regionState LIKE %:regionState%) " +
            "AND (:city IS NULL OR f.city LIKE %:city%) " +
            "AND (:hospitalName IS NULL OR f.facilityName LIKE %:hospitalName%)",
            nativeQuery = true)
    List<Object[]> searchFacilities(
            @Param("country") String country,
            @Param("regionState") String regionState,
            @Param("city") String city,
            @Param("hospitalName") String hospitalName
    );
}
