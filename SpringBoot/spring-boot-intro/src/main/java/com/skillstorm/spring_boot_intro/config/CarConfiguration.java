package com.skillstorm.spring_boot_intro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.skillstorm.spring_boot_intro.beans.Car;
import com.skillstorm.spring_boot_intro.beans.ElectricEngine;
import com.skillstorm.spring_boot_intro.beans.Engine;
import com.skillstorm.spring_boot_intro.beans.GasEngine;
import com.skillstorm.spring_boot_intro.beans.Vehicle;

@Configuration  // tells spring there's some beans in here
public class CarConfiguration {



    /**
     * Manually create beans for spring to manage with @Bean
     *      - registers the bean inside of the BeanFactory
     *          - this allows spring to give you your beans when you ask for them later
     * 
     *      - name allows us to ask for beans by name, rather than by the method
     */
    @Bean(name = "gas")
    Engine gasEngine() {
        return new GasEngine();
    }

    @Bean(name = "electric")
    Engine electricEngine() {
        return new ElectricEngine();
    }

    @Bean(name = {"mustang", "rio"})
    Vehicle gasCar() {
        Car car = new Car(gasEngine());     // constructor injection - using the contstructor to inject a bean from the BeanFactory
        return car;
    }

    /**
     * @Scope
     *      - specifies what type of bean you want - basically its lifecycle
     * 
     *      - bean scopes:
     *          - singleton (default if no scope specified) - only one instance of a bean. every instance references the same bean
     *          - prototype - each bean is different. lazily loaded. 
     *          - application - create a bean for the lifecycle of the app
     *          - request - bean lives for the lifespan of an HTTP request
     *          - session - bean lives for the lifecycle of a user's session
     *          - websocket - bean lives for the lifecycle of a websocket
     */

    @Bean(name = "rivian")
    //@Scope("prototype")
    //@Primary        // @Primary gives this Bean priority over others of the same type
    Vehicle electricCar() {
        Car car = new Car();     
        car.setEngine(electricEngine());    // setter injection - using the setter methods to inject a bean from the BeanFactory
        return car;
    }


}
