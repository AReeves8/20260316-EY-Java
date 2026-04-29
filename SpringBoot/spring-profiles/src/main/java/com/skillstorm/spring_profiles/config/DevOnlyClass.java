package com.skillstorm.spring_profiles.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;

/**
 * @Profile
 *      - limit some bean or configuration to a specific profile
 *      - Spring will only add this bean to the IoC container, if we're in the dev profile
 * 
 */
@Profile("dev")
@Component
public class DevOnlyClass implements CommandLineRunner {

    


    /**
     * Spring Expression Language SpEL
     *      - wraps the input for @Value in a #{ }
     * 
     *      - allows you to basically run Java code inside of @Value
     */

    // calling a method on the value as a string
    @Value("#{'${spring.datasource.url}'.toUpperCase()}")
    private String databaseURL;

    // ternary operator
    @Value("#{${features.max-movie-results} > 50 ? 'HIGH_CAPACITY' : 'LOW__CAPACITY'}")
    private String capacityMode;



    @Override
    public void run(String... args) throws Exception {
        System.out.println("##################################");
        System.out.println("##### DEV PROFILE IS RUNNING #####");
        System.out.println("#####                        #####");
        System.out.println("##### Database running on    #####");
        System.out.println("##### " + databaseURL + "   #####");
        System.out.println("#####                        #####");
        System.out.println("##### App is in              #####");
        System.out.println("##### " + capacityMode + "          #####");

        System.out.println("##################################");
    }
    
}
