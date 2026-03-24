package com.skillstorm.spring_boot_intro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skillstorm.spring_boot_intro.beans.Vehicle;


/**
 * @Component to tell spring to register this class as a bean
 * 
 *      @Service - Stereotype Annotation are more speccific versions of @Component (they do the same thing, but with more context)
 */
@Service
public class CarService {

    // @Autowired                  // asks spring to "wire" us a bean
    // @Qualifier("mustang")       // lets us ask for a specific bean
    // private Vehicle car;

    // Spring treats this the same as using @Autowired like above
    // this is preferred because @Autowired adds complications/inconsistencies in testing
    private final Vehicle car;

    //@Autowired  // starting in Spring 6, you don't ahve to include Autowired on the constructor anymore
    public CarService(@Qualifier("mustang") Vehicle car) {
        this.car = car;
    }


    public void serviceVehicle() {
        System.out.println("Servicing " + this.car);
        car.drive();
    }
}
