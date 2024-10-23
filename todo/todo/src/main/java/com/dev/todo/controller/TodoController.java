package com.dev.todo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.todo.Entity.Todo;
import com.dev.todo.dto.TodoDTO;
import com.dev.todo.mappings.TodoMapper;
import com.dev.todo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;
    
    @Autowired
    private TodoMapper todoMapper;

    @GetMapping
    public ResponseEntity<?> getAllTodos() {
        List<Todo> todos = todoService.getAllTodosByUserId();
        List<TodoDTO> todoDTOs = todoMapper.toDTOList(todos);
        return ResponseEntity.ok(todoDTOs);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
//        return todoService.getTodoById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping
    public ResponseEntity<?> addTodo(@Valid @RequestBody TodoDTO todoDTO) {
    	Todo todo = todoMapper.toEntity(todoDTO);
        Todo createdTodo = todoService.addTodo(todo);
        TodoDTO todoDTOs = todoMapper.toDTO(createdTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoDTOs);
//        return ResponseEntity.ok("Task added successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoDTO todoDTO) {
        Todo todo = todoMapper.toEntity(todoDTO);
        
        return todoService.updateTodo(id, todo)
        		.map(updatedTodo -> ResponseEntity.ok(updatedTodo))
//                .map(updatedTodo -> ResponseEntity.ok("Task updated successfully."))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Task deleted successfully.");
    }
}

