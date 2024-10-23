package com.dev.todo.mappings;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dev.todo.Entity.User;
import com.dev.todo.dto.UserDTO;

@Component
public class UserMapper {

    // Convert UserDTO to User
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Ensure to hash the password before saving
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setMobileNumber(userDTO.getMobileNumber());
        return user;
    }

    // Convert User to UserDTO
//    public static UserDTO toDTO(User user) {
//        if (user == null) {
//            return null;
//        }
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword(user.getPassword()); // Don't expose this in DTO
//        userDTO.setFirstName(user.getFirstName());
//        userDTO.setLastName(user.getLastName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setGender(user.getGender());
//        userDTO.setAge(user.getAge());
//        userDTO.setMobileNumber(user.getMobileNumber());
//        return userDTO;
//    }

    public UserDTO toDTO(User createdUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(createdUser.getUsername());
        userDTO.setPassword(createdUser.getPassword());
        userDTO.setFirstName(createdUser.getFirstName());
        userDTO.setLastName(createdUser.getLastName());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setGender(createdUser.getGender());
        userDTO.setAge(createdUser.getAge());
        userDTO.setMobileNumber(createdUser.getMobileNumber());
        
        return userDTO;
    }

}
