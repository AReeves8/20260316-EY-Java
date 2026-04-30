package com.skillstorm.spring_boot_actuator.actuator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.skillstorm.spring_boot_actuator.models.Movie;
import com.skillstorm.spring_boot_actuator.repositories.MovieRepository;


/**
 * @Endpoint
 *      - lets you create a custom actuator endpoint
 *      - can expose whatever information you want
 *          - can work with the data nad info from within your app 
 * 
 *      - Operation Types
 *          - @ReadOperation    GET /actuator/endpoint-name
 *          - @WriteOperation   POST /actuator/endpoint-name
 *          - @DeleteOperation  DELETE /actuator/endpoint-name
 *          - @Selector         equivalent of @PathVariable but for the custom endpoint
 */
@Component
@Endpoint(id = "movie-stats")       // endpoint will be: actuator/movie-stats
public class MovieStatsEndpoint {

    private final MovieRepository movieRepository;
    
    public MovieStatsEndpoint(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @ReadOperation
    public Map<String, Object> movieStats() {

        List<Movie> allMovies = movieRepository.findAll();

        // stat: number of movies per genre
        Map<String, Long> moviesbyGenre = allMovies.stream()
            .collect(Collectors.groupingBy(
                movie -> movie.getGenre().getDisplayName(), 
                Collectors.counting()
            ));

        // stat: average rating
        double avgRating = allMovies.stream()
            .mapToInt(Movie::getRating)
            .average()
            .orElse(0);     // return 0 if average cannot be calculated

        // stat: every movie rated at an 8 or higher
        List<Movie> highestRatedMovies = allMovies.stream()
            .filter(movie -> movie.getRating() >= 8)
            .toList();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMovies", allMovies.size());
        stats.put("moviesbyGenre", moviesbyGenre);
        stats.put("highestratedMovies", highestRatedMovies);
        stats.put("averageMovieRating", avgRating);

        return stats;
    }

}
