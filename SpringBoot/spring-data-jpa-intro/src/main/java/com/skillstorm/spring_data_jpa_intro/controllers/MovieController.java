package com.skillstorm.spring_data_jpa_intro.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.spring_data_jpa_intro.models.Movie;
import com.skillstorm.spring_data_jpa_intro.services.MovieService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/movies")

// telling our server where to expect requests from so we don't get CORS errors
@CrossOrigin({"http://127.0.0.1:5500"})      // don't want @CrossOriging("*") - this allows everything
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) Integer rating) {

        // if the param is not given, then it will be null

        List<Movie> movies = service.getMovies(rating);
        if(movies == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMoviebyId(@PathVariable int id) {

        Movie movie = service.getMovieById(id);
        if(movie == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // if you anted a speific endpoint just to query by rating:

    // @GetMapping("/rating")
    // public ResponseEntity<List<Movie>> getMoviesByRating(@RequestParam int rating) {

    //     List<Movie> movies = service.getMoviesByRating(rating);
    //     if(movies == null) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     return new ResponseEntity<>(movies, HttpStatus.OK);
    // }

    // @Valid is to make sure the Jakarta Validations are checked
    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie newMovie) {
        Movie movie = service.saveMovie(newMovie);
        if(movie == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @Valid @RequestBody Movie newMovie) {
        Movie movie = service.saveMovie(newMovie);
        if(movie == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        boolean isDeleted = service.deleteMovie(id);
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       return ResponseEntity.notFound().build();
    }
}
