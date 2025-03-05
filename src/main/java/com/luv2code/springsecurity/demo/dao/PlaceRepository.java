package com.luv2code.springsecurity.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springsecurity.demo.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Place findByName(String name);
}