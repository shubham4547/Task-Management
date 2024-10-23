package com.dev.todo.mappings;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dev.todo.Entity.Todo;
import com.dev.todo.dto.TodoDTO;

@Component
public class TodoMapper {

    // Convert Todo entity to TodoDTO
    public TodoDTO toDTO(Todo todo) {
        if (todo == null) {
            return null;
        }
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todo.getId());
        todoDTO.setTitle(todo.getTitle());
        todoDTO.setDescription(todo.getDescription());
        todoDTO.setCompleted(todo.isCompleted());
        todoDTO.setCreatedAt(todo.getCreatedAt());
        todoDTO.setUpdatedAt(todo.getUpdatedAt());
        return todoDTO;
    }

    // Convert TodoDTO to Todo entity
    public Todo toEntity(TodoDTO todoDTO) {
        if (todoDTO == null) {
            return null;
        }

        Todo todo = new Todo();
        todo.setId(todo.getId());   //
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());
        return todo;
    }
    
    public List<TodoDTO> toDTOList(List<Todo> todos) {
        return todos.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
    }
}

