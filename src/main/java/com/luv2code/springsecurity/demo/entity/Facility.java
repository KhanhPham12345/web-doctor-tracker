package com.luv2code.springsecurity.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facilityId;

    @Column(name = "facilityName", nullable = false)
    private String facilityName;

    private String unitNo;
    private String block;
    private String street;
    private String buildingName;
    private String regionState;
    private String city;
    private String country;
    private String postalCode;

    private Integer totalDoctor;
    private Integer totalSpecialty;
    private Integer totalSubSpecialty;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    // Getters and setters
    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getTotalDoctor() {
        return totalDoctor;
    }

    public void setTotalDoctor(Integer totalDoctor) {
        this.totalDoctor = totalDoctor;
    }

    public Integer getTotalSpecialty() {
        return totalSpecialty;
    }

    public void setTotalSpecialty(Integer totalSpecialty) {
        this.totalSpecialty = totalSpecialty;
    }

    public Integer getTotalSubSpecialty() {
        return totalSubSpecialty;
    }

    public void setTotalSubSpecialty(Integer totalSubSpecialty) {
        this.totalSubSpecialty = totalSubSpecialty;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
