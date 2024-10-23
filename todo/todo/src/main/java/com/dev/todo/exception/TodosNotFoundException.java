package com.dev.todo.exception;

public class TodosNotFoundException extends RuntimeException {
    public TodosNotFoundException(String message) {
        super(message);
    }
}
