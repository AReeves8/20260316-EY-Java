package com.skillstorm.spring_mvc_intro.services;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.stereotype.Service;

import com.skillstorm.spring_mvc_intro.models.User;

//import com.skillstorm.spring_mvc_intro.repositories.UserRepository;

@Service        // stereotype annotation for service classes
public class UserService {

    /**
     * Service Layer is used to handle business logic
     */

    // Spring can give us a bean for our repository
    // private final UserRepository userRepository;
    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    public List<User> getAllUsers() {

        // simulating calling the database to return a list of all the users
        List<User> users = new LinkedList<>();
        users.add(new User(1, "Austin", "Reeves", "areeves@skillstorm.com", "password@123"));
        users.add(new User(2, "Jon", "Walker", "jwalker@skillstorm.com", "password@123"));
        users.add(new User(3, "Aidan", "Pavlik", "apavlik@skillstorm.com", "password@123"));
        return users;
    }

    public User getUserById(long id) throws IllegalArgumentException, NameNotFoundException {

        // early exit - checking something that will exit this method at the beginning so you can save performance
        if(id < 0) {
            throw new IllegalArgumentException("Id cannot be negative.");
        }

        // simulating calling the database to find a list of all the users
        List<User> users = new LinkedList<>();
        users.add(new User(1, "Austin", "Reeves", "areeves@skillstorm.com", "password@123"));
        users.add(new User(2, "Jon", "Walker", "jwalker@skillstorm.com", "password@123"));
        users.add(new User(3, "Aidan", "Pavlik", "apavlik@skillstorm.com", "password@123"));

        // returning the user with the specified ID
        for(User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }

        throw new NameNotFoundException("No user with that id exists.");

    }

    public List<User> getUsersByName(String firstName, String lastName) throws NameNotFoundException {
        
        // simulating calling the database to find a list of all the users
        List<User> users = new LinkedList<>();
        users.add(new User(1, "Austin", "Reeves", "areeves@skillstorm.com", "password@123"));
        users.add(new User(2, "Jon", "Walker", "jwalker@skillstorm.com", "password@123"));
        users.add(new User(3, "Aidan", "Pavlik", "apavlik@skillstorm.com", "password@123"));
        users.add(new User(4, "Austin", "Smith", "asmith@skillstorm.com", "password@123"));
        users.add(new User(5, "Donald", "Reeves", "asmith@skillstorm.com", "password@123"));

        List<User> foundUsers = new LinkedList<>();
        // returning the users with either of the given names
        for(User user: users) {
            if (user.getFirstName().equals(firstName) || user.getLastName().equals(lastName)) {
                foundUsers.add(user);
            }
        }

        if(foundUsers.size() == 0) {
            throw new NameNotFoundException("No user with those names exists.");
        }

        return foundUsers;
    }

    public User createUser(User user) {

        // simulates creating a new user and the database generating the ID
        return new User(5, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }


    public User updateUser(long id, User user) throws IllegalArgumentException {

        // early exit - checking something that will exit this method at the beginning so you can save performance
        if(id < 0) {
            throw new IllegalArgumentException("Id cannot be negative.");
        }

        // normally would save new values to DB and return any updated info that the DB generates
        return user;
    }

    public void deleteUser(long id) throws IllegalArgumentException {

        // early exit - checking something that will exit this method at the beginning so you can save performance
        if(id < 0) {
            throw new IllegalArgumentException("Id cannot be negative.");
        }

        // call DB to delete user
    }

}
