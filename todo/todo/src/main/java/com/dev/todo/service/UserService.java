package com.dev.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	
import org.springframework.stereotype.Service;

import com.dev.todo.Entity.User;
import com.dev.todo.constants.AppConstant;
import com.dev.todo.dto.LoginDTO;
import com.dev.todo.dto.UserDTO;
import com.dev.todo.exception.UserAlreadyExistsException;
import com.dev.todo.repository.UserRepository;
import com.dev.todo.security.JwtAuthenticationFilter;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Lazy
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	@Lazy
    private BCryptPasswordEncoder passwordEncoder;
	
//	public boolean isAdmin(String username) {
//        Employee user = employeeRepository.findByUsername(username);
//        return user != null && "ADMIN".equals(user.getRole());
//    }
	
//    public Optional<Employee> authenticate(LoginDTO pLoginDTO) {
////      Optional<Employee> dEmployee = employeeRepository.findByUsernameAndPassword(pLoginDTO.getUsername(),pLoginDTO.getPassword());
////    	Optional<Employee> dEmployee = employeeRepository.findByUsername(pLoginDTO.getUsername());
//        
//        // Check if employee exists and matches the provided password
//        if (dEmployee.isPresent() && passwordEncoder.matches(pLoginDTO.getPassword(), dEmployee.get().getPassword())) {
//            return dEmployee; // Return the employee if authentication is successful
//        }
//        return Optional.empty(); // Return empty if authentication fails
//    }
	
	public Optional<User> getUser(String pUsername) {
		return userRepository.findByUsername(pUsername);
	}
	
//	//This will be used in todo service
//	public Long getUserId(String pUsername) {
//		return userRepository.findUserIdByUsername(pUsername);
//	}
	
//	public List<User>getAllEmployees(){
//		return employeeRepository.findAll();
//	}
//	
//	public Optional<List<User>> filterUsers(String pName, String pEmail, String pRole) {
//		return employeeRepository.findByNameContainingAndEmailContainingAndRoleContaining(pName, pEmail, pRole);
//	}
	
	public User addUser(User pUser) {
		Optional<User> dUser = userRepository.findByUsername(pUser.getUsername());
		if (dUser.isPresent()) {
	        throw new UserAlreadyExistsException(pUser.getUsername());
	    }
		pUser.setPassword(passwordEncoder.encode(pUser.getPassword()));
	    pUser.setRole(AppConstant.Role.USER);
	    pUser.setStatus(AppConstant.Status.ACTIVE);
	    pUser.setCreatedAt(LocalDateTime.now());
	    pUser.setCreatedBy(AppConstant.USER);
	    return userRepository.save(pUser);
    }
	
	public Optional<User> updateUser(User pUser) {
            Optional<User> dUser = userRepository.findByUsername(pUser.getUsername());
            if(dUser.isPresent()) {
            	User lUser = dUser.get();
            	lUser.setUsername(pUser.getUsername());
            	lUser.setPassword(passwordEncoder.encode(pUser.getPassword()));
            	lUser.setFirstName(pUser.getFirstName());
            	lUser.setLastName(pUser.getLastName());
            	lUser.setEmail(pUser.getEmail());
            	lUser.setAge(pUser.getAge());
            	lUser.setMobileNumber(pUser.getMobileNumber());
            	lUser.setUpdatedAt(LocalDateTime.now());
            	lUser.setUpdatedBy(pUser.getUsername());
            	return Optional.of(userRepository.save(lUser));
            }
            return Optional.empty();
    }

//    public boolean deleteEmployee(User pEmployee) {
//    	Optional<User> dEmployee = employeeRepository.findById(pEmployee.getEmployeeId());
//        if (dEmployee.isPresent()) {
//        	User lEmployee = dEmployee.get();
//            employeeRepository.deleteById(lEmployee.getEmployeeId());
//            return true;
//        }
//        return false;
//    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found !!"));
		return userDetails;
	}


	
}
