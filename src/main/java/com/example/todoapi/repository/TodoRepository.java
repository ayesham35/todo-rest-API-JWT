package com.example.todoapi.repository;

import com.example.todoapi.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser_UsernameOrderByCreatedAtDesc(String username);

    List<Todo> findByUser_UsernameAndCompletedOrderByCreatedAtDesc(
            String username, boolean completed);

    Optional<Todo> findByIdAndUser_Username(Long id, String username);

    long deleteByIdAndUser_Username(Long id, String username);

}
