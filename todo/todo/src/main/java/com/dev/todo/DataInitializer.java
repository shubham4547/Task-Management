package com.dev.todo;

import com.dev.todo.Entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import com.dev.todo.constants.AppConstant;
import com.dev.todo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	

@Component
public class DataInitializer implements CommandLineRunner {


    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
    	Optional<User> dUser = userRepository.findByUsername(AppConstant.ADMIN);   	
    	if (dUser.isEmpty()) {
            User lUser = new User();
            lUser.setUsername(AppConstant.ADMIN);
            lUser.setPassword(passwordEncoder.encode(AppConstant.ADMIN)); 
            lUser.setFirstName(AppConstant.ADMIN);
            lUser.setLastName(AppConstant.ADMIN);
            lUser.setRole(AppConstant.Role.USER);
            lUser.setStatus(AppConstant.Status.ACTIVE);
            lUser.setEmail("admin@gmail.com");
            lUser.setAge(24);
            lUser.setGender("Male");
            lUser.setMobileNumber(9878745410L);
            lUser.setCreatedAt(LocalDateTime.now());
            lUser.setCreatedBy(AppConstant.ADMIN);
            userRepository.save(lUser);
            System.out.println("Admin user created: " + lUser.getUsername());
        } else {
            System.out.println("Admin user already exists: " + AppConstant.ADMIN);
        }
    }
}

