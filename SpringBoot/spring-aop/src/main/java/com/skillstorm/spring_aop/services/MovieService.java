package com.skillstorm.spring_aop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.spring_aop.models.Director;
import com.skillstorm.spring_aop.models.Movie;
import com.skillstorm.spring_aop.repositories.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final DirectorService directorService;

    public MovieService(MovieRepository repository, DirectorService directorService) {
        this.repository = repository;
        this.directorService = directorService;
    }


    /**
     * one method to return movies
     *      IF some option to filter down movies is present, look for it
     *      otherwise, return all movies
     */
    public List<Movie> getMovies(Integer rating) {

        if(rating == null) {
            return repository.findAll();    // built in method to retreive all rows in the table
        }

        repository.findByRatingGreaterThanEqual(rating);
        Optional<List<Movie>> movies = repository.findByRatingGreaterThanEqual(rating);
        if(movies.isPresent()) {
            return movies.get();
        }
        // throw an exception, return null, whatever error handling scenario you'd like
        return null;
    }


    public List<Movie> getAllMovies() {
        return repository.findAll();    // built in method to retreive all rows in the table
    }

    public Movie getMovieById(int id) {

        // optional will either contain a returned object, or it won't. if it doesm return it, if not, then nothing was found in the db
        Optional<Movie> movie = repository.findById(id);
        if(movie.isPresent()) {
            return movie.get();
        }

        // throw an exception, return null, whatever error handling scenario you'd like
        return null;
    }

    // public List<Movie> getMoviesByRating(int rating) {

    //     // optional will either contain a returned object, or it won't. if it doesm return it, if not, then nothing was found in the db
    //     Optional<List<Movie>> movies = repository.findByRatingGreaterThanEqual(rating);
    //     if(movies.isPresent()) {
    //         return movies.get();
    //     }

    //     // throw an exception, return null, whatever error handling scenario you'd like
    //     return null;
    // }


    @Transactional // if any one of the DB operations fail, rollback all the previous ones
    public Movie saveMovie(Movie movie) {

        System.out.println("Before director check: " + movie.toString());

        Director director = directorService.findDirectorById(movie.getDirector().getId());
        if(director == null) {

            // creating a new director if the one that was given doesn't exist
            director = directorService.saveDirector(movie.getDirector());

            // could just throw error instead if you wanted
        }

        // make sure Movie has the full director object
        movie.setDirector(director);


        System.out.println("After set director: " + movie.toString());

        /**
         * .save() does both inserts and updates
         *      calls a isNew() nethod to determine if the record exists or not
         *          - usually going to check if the given PK exists
         */
        return repository.save(movie);  // return a new object with any updated values
    }

    public boolean deleteMovie(Integer id) {
        if(getMovieById(id) == null) {
            // throw some error to tell the client that the given movie doesn't exist
            return false;
        }

        // does nothing if id is not found (aka movie doesn't exist)
        repository.deleteById(id);
        return true;

    }
    
}
