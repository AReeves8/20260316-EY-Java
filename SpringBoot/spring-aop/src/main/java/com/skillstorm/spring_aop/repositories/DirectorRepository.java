package com.skillstorm.spring_aop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.spring_aop.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
