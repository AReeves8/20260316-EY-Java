package com.skillstorm.spring_data_jpa_intro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.spring_data_jpa_intro.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
