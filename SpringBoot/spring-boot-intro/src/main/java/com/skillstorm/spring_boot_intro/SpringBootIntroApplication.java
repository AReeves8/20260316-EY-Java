package com.skillstorm.spring_boot_intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.skillstorm.spring_boot_intro.beans.Car;
import com.skillstorm.spring_boot_intro.beans.Vehicle;
import com.skillstorm.spring_boot_intro.services.CarService;

/**
 * @SpringBootApplication
 * 		combo of three other annotations
 * 			@Configuration - tells spring that this is a configuration class (a class that has beans that spring needs to manage)
 * 			@ComponentScan - search within the package for any @Component annotations (these create beans spring should manage)
 * 			@EnableAutoConfiguration - tell spring boot to auto-configure the app context with dependencies/tools we need (like a web server)
 */
@SpringBootApplication
public class SpringBootIntroApplication implements CommandLineRunner {

	@Autowired
	public CarService service;

	public static void main(String[] args) {


		/**
		 * BeanFactory vs ApplicationContext
		 * 		- BeanFactory LAZILY loads beans
		 * 		- ApplicationContext EAGERILY loads beans
		 * 			- usually what is preferred
		 * 
		 */

		// executes ApplicationContext
		ApplicationContext context = SpringApplication.run(SpringBootIntroApplication.class, args);

		// what you would do before spring
		// Vehicle car = new Car(new ElectricEngine());
		// car.drive();

		// with spring, we just ask it to give us a bean
		Vehicle car = (Vehicle) context.getBean("rivian");
		car.drive();

	}


	/**
	 * COMMAND LINE RUNNER
	 * 		- funcitonal interface: interface with only one method
	 * 			- can be ran with lambdas to create anonymous inner classes
	 * 
	 * 		- method that runs AFTER your app context is loaded. 
	 * 			- usually used to load your application with data/beans as soon as possible
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {

		// using our CarService bean to service the car it was created with
		service.serviceVehicle();
	}

}
