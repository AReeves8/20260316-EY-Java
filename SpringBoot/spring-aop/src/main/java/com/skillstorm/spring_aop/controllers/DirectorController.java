package com.skillstorm.spring_aop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.spring_aop.models.Director;
import com.skillstorm.spring_aop.services.DirectorService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {

    private final DirectorService service;

    public DirectorController(DirectorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Director>> getAllDirectors(
        @RequestParam(defaultValue = "2") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(service.getAllDirectors(page, size, sortBy), HttpStatus.OK);
    }
    
}
