package com.skillstorm.spring_mvc_intro.dtos;

import com.skillstorm.spring_mvc_intro.models.User;

/**
 * Design Pattern: Data Transfer Objects (DTOs)
 *      - to be a version of a model that can be transferred to different services. 
 *  
 * 
 *      common to have multiple DTOs for a model. 
 *          you might want a UserCreationDto that has no id but DOES include a password
 */
public record UserDTO(
    long id, 
    String firstName, 
    String lastName, 
    String email
) {

    // User => UserDTO
    public static UserDTO convertToDto(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    // UserDTO => User
    public static User convertToUser(UserDTO userDto) {
        return new User(userDto.id(), userDto.firstName, userDto.lastName(), userDto.email(), null);
    }

}
