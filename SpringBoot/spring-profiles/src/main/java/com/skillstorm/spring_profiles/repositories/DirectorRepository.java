package com.skillstorm.spring_profiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.spring_profiles.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
