package com.example.todoapi.service;

import com.example.todoapi.dto.todo.TodoRequest;
import com.example.todoapi.dto.todo.TodoResponse;
import com.example.todoapi.entity.Todo;
import com.example.todoapi.entity.User;
import com.example.todoapi.exception.ResourceNotFoundException;
import com.example.todoapi.repository.TodoRepository;
import com.example.todoapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TodoResponse> findAll(String username) {
        return todoRepository.findByUser_UsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> findByStatus(String username, boolean completed) {
        return todoRepository.findByUser_UsernameAndCompletedOrderByCreatedAtDesc(username, completed)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoResponse findById(Long id, String username) {
        Todo todo = todoRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));
        return toResponse(todo);
    }

    public TodoResponse create(TodoRequest request, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Todo todo = Todo.builder()
                .title(request.getTitle().trim())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .user(owner)
                .build();

        return toResponse(todoRepository.save(todo));
    }

    public TodoResponse update(Long id, TodoRequest request, String username) {

        Todo todo = todoRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));

        todo.setTitle(request.getTitle().trim());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        return toResponse(todoRepository.save(todo));
    }

    public TodoResponse toggleCompleted(Long id, String username) {
        Todo todo = todoRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));
        todo.setCompleted(!todo.isCompleted());
        return toResponse(todoRepository.save(todo));
    }

    public void delete(Long id, String username) {
        long deleted = todoRepository.deleteByIdAndUser_Username(id, username);
        if (deleted == 0) {
            throw new ResourceNotFoundException("Todo not found: " + id);
        }
    }

    // -----mapping-----

    private TodoResponse toResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
