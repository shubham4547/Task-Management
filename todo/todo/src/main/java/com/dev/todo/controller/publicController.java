package com.dev.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.todo.Entity.User;

@RestController
@RequestMapping("/info")
public class publicController {
	
	private publicController employeeService;

	@GetMapping("/home")
	public String goHome() {
		return "Home";
	}
	
	@PostMapping("/add")
	public User addEmployee(@RequestBody User pEmployee) {
		return employeeService.addEmployee(pEmployee);
	}
}
