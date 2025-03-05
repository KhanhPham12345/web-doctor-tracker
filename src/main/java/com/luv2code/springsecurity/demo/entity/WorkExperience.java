package com.luv2code.springsecurity.demo.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "work_experience")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    // Constructors
    public WorkExperience() {
    }

    public WorkExperience(String description) {
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public User getDoctors() {
		return doctor;
	}

	public void setDoctors(User doctor) {
		this.doctor = doctor;
	}
    
}
