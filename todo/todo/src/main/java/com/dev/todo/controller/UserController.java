package com.dev.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.todo.Entity.User;
import com.dev.todo.dto.ErrorResponse;
import com.dev.todo.dto.JwtRequest;
import com.dev.todo.dto.JwtResponse;
import com.dev.todo.dto.LoginDTO;
import com.dev.todo.dto.SuccessResponse;
import com.dev.todo.dto.UserDTO;
import com.dev.todo.exception.UserAlreadyExistsException;
import com.dev.todo.mappings.UserMapper;
import com.dev.todo.security.JwtAuthenticationFilter;
import com.dev.todo.security.JwtBlacklistService;
import com.dev.todo.security.JwtHelper;
import com.dev.todo.service.UserService;

import jakarta.validation.Valid;

//@CrossOrigin(origins = "http://localhost:5173") // Allow specific origin
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	private UserService userService;
	@Autowired
	private JwtBlacklistService jwtBlacklistService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
    private AuthenticationManager manager;
	
	@Autowired
    private JwtHelper helper;
	
	@PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		this.doAuthenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	private void doAuthenticate(String username, String password) {
		System.out.println("Authenticating user: " + username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
            System.out.println("Authentication successful for user: " + username);
        } catch (BadCredentialsException e) {
        	 System.out.println("Authentication failed for user: " + username);
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
	}
	
	@GetMapping()
	public ResponseEntity<UserDTO> getUser() {
		System.out.println(filter.username); 
	    Optional<User> lUser = userService.getUser(filter.username); // Assume this method returns Optional<User>
	    
	    if (lUser.isPresent()) {
	        UserDTO userDTO = userMapper.toDTO(lUser.get()); // Assuming your method returns Optional<UserDTO>
	        return ResponseEntity.ok(userDTO);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if user not found
	    }
	}
	
	@PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Strip "Bearer " from the token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Blacklist the token
        jwtBlacklistService.blacklistToken(token);
        return ResponseEntity.ok("Logout successful");
    }
	
//	@GetMapping("/get")
//	public List<User> getAll(){
//		return userService.getAllEmployees();
//	}
	
//	@GetMapping("/getfilter")
//	public Optional<List<User>> getUSers(@RequestParam(required = false) String name,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) String role) {
//        return employeeService.filterUsers(name, email, role);
//	}
	
	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO pUserDTO) {
		System.out.println(pUserDTO.toString());
	    User lUser = userMapper.toEntity(pUserDTO);
	    
	    try {
	        User createdUser = userService.addUser(lUser);
//	        UserDTO createdUserDTO = userMapper.toDTO(createdUser);
	        SuccessResponse successResponse = new SuccessResponse("User successfully registered");
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	        
	    } catch (UserAlreadyExistsException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                             .body(new ErrorResponse(e.getMessage()));
	    }
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO pUserDTO) {
	    // Set the username from the JWT token to ensure it cannot be changed
	    pUserDTO.setUsername(filter.username); // Ensure the username in the DTO is the one being updated
	    User lUser = userMapper.toEntity(pUserDTO);
	    try {
	        Optional<User> updatedUser = userService.updateUser(lUser);
	        return updatedUser.map(user -> {
	        	UserDTO updatedUserDTO = userMapper.toDTO(user);
	            return ResponseEntity.ok(updatedUserDTO);
	        }).orElseGet(() -> ResponseEntity.notFound().build());
	    } catch (Exception e) {
	        // Handle other potential exceptions (e.g., database issues)
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ErrorResponse("An error occurred while updating the user: " + e.getMessage()));
	    }
	}

	
//	@DeleteMapping("/delete")
//	@PreAuthorize("hasRole('admin')")
//    public ResponseEntity<Void> deleteUser(@RequestBody User pUser) {
//        return employeeService.deleteUser(pUser) ? ResponseEntity.noContent().build() :
//                ResponseEntity.notFound().build();
//    }
	
	
}
