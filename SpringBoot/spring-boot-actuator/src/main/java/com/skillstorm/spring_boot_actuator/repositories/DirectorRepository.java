package com.skillstorm.spring_boot_actuator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.spring_boot_actuator.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
