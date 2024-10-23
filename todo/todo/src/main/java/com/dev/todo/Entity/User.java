package com.dev.todo.Entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dev.todo.constants.AppConstant.Role;
import com.dev.todo.constants.AppConstant.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@Column(nullable = false, unique = true)
	private String username;
	
	@NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
	private String firstName;
	
	@NotBlank(message = "Last name is mandatory")
	@Size(max = 50, message = "Last name cannot exceed 50 characters")
	private String lastName;
	
	@Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
	private String email;
	
	@Size(max = 10, message = "Gender cannot exceed 10 characters")
	private String gender;
	
	@Min(value = 0, message = "Age must be a positive number")
	private int age;
	
	@NotNull(message = "Mobile number is mandatory")
	@Column(nullable = false)
	private Long mobileNumber;
	
	//role and status is for furture use
	private Role role;
	
	private Status status;
	
	private LocalDateTime createdAt;
	
	private String createdBy;
	
	private LocalDateTime updatedAt;
	
	private String updatedBy;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
