package com.dev.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dev.todo.Entity.Todo;
import com.dev.todo.Entity.User;
import com.dev.todo.constants.AppConstant;
import com.dev.todo.exception.TodoNotFoundException;
import com.dev.todo.exception.TodosNotFoundException;
import com.dev.todo.repository.TodoRepository;
import com.dev.todo.repository.UserRepository;
import com.dev.todo.security.JwtAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private JwtAuthenticationFilter filter;

    public List<Todo> getAllTodosByUserId() {
    	long lUserId = userRepository.findUserIdByUsername(filter.username);
    	List<Todo> lToDos = todoRepository.findAllTodosByUserId(lUserId);
//        if (lToDos.isEmpty()) {
//            throw new TodosNotFoundException("No todos found for user: " + filter.username);
//        }
        return lToDos;
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo addTodo(Todo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUserId(userRepository.findUserIdByUsername(filter.username));
        return todoRepository.save(todo);
    }
    

    public Optional<Todo> updateTodo(Long id, Todo updatedTodo) {
        return Optional.ofNullable(todoRepository.findById(id).map(existingTodo -> {
            existingTodo.setTitle(updatedTodo.getTitle());
            existingTodo.setDescription(updatedTodo.getDescription());
            existingTodo.setCompleted(updatedTodo.isCompleted());
            existingTodo.setUpdatedAt(LocalDateTime.now());
            return todoRepository.save(existingTodo);
        }).orElseThrow(() -> new TodoNotFoundException("Todo with ID " + id + " not found.")));
    }

    public void deleteTodo(Long id) {
    	if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo with ID " + id + " not found.");
        }
        todoRepository.deleteById(id);
    }
}

