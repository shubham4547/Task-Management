package com.dev.todo.constants;

import java.time.LocalDateTime;

public class AppConstant {
	
	public static final String ADMIN = "admin";
	public static final String USER = "user";
	public static final LocalDateTime TIME = LocalDateTime.now();
	
	public enum Role {
	    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

	    private final String roleName;

	    Role(String roleName) {
	        this.roleName = roleName;
	    }

	    public String getRoleName() {
	        return roleName;
	    }
	}
	
	public enum Status {
		ACTIVE("ACTIVE"), Disabled("DISABLED");
		
		private String status;
		Status(String status) {
			this.status = status;
		}
		

	    public String getStatus() {
	        return status;
	    }
	}
	
}
