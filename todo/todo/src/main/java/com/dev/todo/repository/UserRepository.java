package com.dev.todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.dev.todo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsernameAndPassword(String pUsername, String pPassword);

	Optional<User> findByUsername(String pUsername);
	
	@Query("SELECT u.userId FROM User u WHERE u.username = :username")
	Long findUserIdByUsername(@Param("username") String pUsername);

//	Optional<List<User>> findByNameContainingAndEmailContainingAndRoleContaining(String pName, String pEmail, String pRole);
	
	


}
