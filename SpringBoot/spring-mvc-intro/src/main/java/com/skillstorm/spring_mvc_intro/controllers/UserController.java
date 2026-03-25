package com.skillstorm.spring_mvc_intro.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.spring_mvc_intro.models.User;
import com.skillstorm.spring_mvc_intro.services.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;






/**
 * @RestController vs @Controller
 *      - @Controller is just a stereotype annotation for controller classes
 * 
 *      - @RestController is a more specific version of Controller
 *          - implicitly adds @ResponseBody to each endpoint method
 *              - automatically uses Jackson Object Mapper to convert java <-> json
 */
@RestController
@RequestMapping("/api/v1/users")       // add a pre-fix route to all endpoints in the class
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // @RequestMapping creates an endpoint that will be exposed in our web app - defaults to GET
    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hello World! :)";
    }


    /**
     * C - POST
     * R - GET
     * U - PUT
     * D - DELETE
     */
    
    /**
     * GET /api/v1/users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();   // returned Java objects will be converted to JSON using Jackson
    }

    /**
     * GET /api/v1/users/{id}
     *      - curly braces create a variable that can be anything
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") long userId) {

        /**
         * @PathVariable retrieves a corresponding variable out of the URL path of the request
         *      - name is only required if the method param has a different name than what is in the path
         */

        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<User>(user, HttpStatus.OK);   // returns user data with a 200 status code
        } catch(IllegalArgumentException exception) {

            // WARNING: be careful sending error messages to frontend to avoid giving sensitive details away
            return ResponseEntity.badRequest().header("message", exception.getMessage()).build();   // returns no data and a 400 status code

        } catch(NameNotFoundException exception) {
            return ResponseEntity.notFound().header("message", exception.getMessage()).build();     // returns no data and a 404 status code

        } catch(Exception exception) {
            // this means something we didn't anticipate went wrong
            return ResponseEntity.internalServerError().header("message", "Oops. Something went wrong.").build(); // returns a 500
        }
    }
    

    /**
     * GET /api/v1/users/name?first-name=someValue&last-name=someOtherValue
     *      - everything after the ? is query params
     *          - query params are separated by &
     */
    @GetMapping("/name")
    public ResponseEntity<List<User>> getUserByName(
        @RequestParam(name = "first-name") String firstName,
        @RequestParam(name = "last-name") String lastName
    ) {

        /**
         * @RequestParam retrieves a corresponding query param out of the URL
         *      - name is only required if the method param has a different name than what is in the path
         */

        try {
            List<User> users = userService.getUsersByName(firstName, lastName);
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);   // returns user data with a 200 status code
        } catch(NameNotFoundException exception) {
            return ResponseEntity.notFound().header("message", exception.getMessage()).build();     // returns no data and a 404 status code
            
        } catch(Exception exception) {
            // this means something we didn't anticipate went wrong
            return ResponseEntity.internalServerError().header("message", "Oops. Something went wrong.").build(); // returns a 500
        }
    }

    /**
     * POST /api/v1/users
     */
    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
    
        /**
         * @RequestBody takes the JSON data in the body of the request and maps it to the specified object
         *      - if no matching properties are found, object will be null
         *      - if only some properties are found, then those will have values, the rest will be defaults
         */

        try {
            User newUser = userService.createUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);   // returns user data with a 201 status code
        } catch(Exception exception) {
            // this means something we didn't anticipate went wrong
            return ResponseEntity.internalServerError().header("message", "Oops. Something went wrong.").build(); // returns a 500
        }
    }

    /**
     * PUT /api/v1/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User newUserInfo) {
        try {
            User newUser = userService.updateUser(id, newUserInfo);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);   // returns user data with a 200 status code
        } catch(IllegalArgumentException exception) {
            return ResponseEntity.badRequest().header("message", exception.getMessage()).build();   // returns no data and a 400 status code
        } catch(Exception exception) {
            // this means something we didn't anticipate went wrong
            return ResponseEntity.internalServerError().header("message", "Oops. Something went wrong.").build(); // returns a 500
        }
    }


    /**
     * DELETE /api/v1/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);   // returns user data with a 200 status code
        } catch(IllegalArgumentException exception) {
            return ResponseEntity.badRequest().header("message", exception.getMessage()).build();   // returns no data and a 400 status code
        } catch(Exception exception) {
            // this means something we didn't anticipate went wrong
            return ResponseEntity.internalServerError().header("message", "Oops. Something went wrong.").build(); // returns a 500
        }
    }

    

}
