package com.dev.todo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TodoDTO {
	private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private Long userId; 

    // Getters and setters omitted for brevity
}
