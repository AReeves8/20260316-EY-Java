package com.skillstorm.spring_mvc_intro.repositories;

import org.springframework.stereotype.Repository;


/**
 * Stereotype Annotations
 *      - more specific versions of @Component
 *      - they do the same thing as @Component
 *          - Tells spring that this class is a bean that Spring needs to manage
 */
@Repository     // stereotype annotation for repositories
public interface UserRepository {

    /**
     * interactions with your database
     *  
     * more on these in Spring Data JPA
     */
}
