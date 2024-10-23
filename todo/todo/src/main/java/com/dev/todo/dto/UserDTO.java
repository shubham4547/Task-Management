package com.dev.todo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
	
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String gender;
	
	private int age;
	
	private Long mobileNumber;
}
