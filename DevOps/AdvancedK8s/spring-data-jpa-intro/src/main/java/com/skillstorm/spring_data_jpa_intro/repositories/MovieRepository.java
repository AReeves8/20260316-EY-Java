package com.skillstorm.spring_data_jpa_intro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.spring_data_jpa_intro.models.Movie;

/**
 * Repositories talk to your database
 *      no business logic whatsoever
 * 
 * 
 *      JpaRepository extends PagingAndSortingRepository extends CrudRepository
 * 
 *      crud repo - gives basic methods for CRUD operations
 *      paging and sorting repo - gives pagination and sorting functionality
 *      jpa repo - all of the above, plus more!!
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {


    /**
     * JPA uses the method signature to create queries
     *      looks for specific keywords and properties from the above mentioned object
     * 
     *      https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
     */
    Optional<List<Movie>> findByRatingGreaterThanEqual(int rating);



    /**
     * @Query and @NativeQuery
     *      - these let you write your own queries
     *      - @Query usues JPQL
     *          - unless you specify native = true      BAD
     *      - @NativeQuery uses SQL                     BAD
     *          - ONLY TIME NATIVE QUERIES ARE OK, is if your db has some uber-complicated query that does magic for your app
     *          
     *      
     *      JPQL
     *          - add in placeholders for variables (?1, ?2, ?3, etc.)
     *              - instead, you can use :param-name
     *          - use @Param in method signature to specify the variables
     */
    //@Query("update Movie m set m.title = ?2 where id = ?1")
    @Query("update Movie m set m.title = :new-title where id = :movie-id")
    @Modifying          // use with an @Query that does an insert, update, or delete
    @Transactional      // for Spring Boot transaction management
    public int updateMovieTitle(@Param("movie-id") int id, @Param("new-title") String newTitle);



    /**
     * @Transactional
     *      - treats a method as a transaction
     *          - multiple tasks executed as one unit of work 
     *          - "all or none" - if even one task fails, then treat it like every task failed and roll back anything that succeeded
     */

}
