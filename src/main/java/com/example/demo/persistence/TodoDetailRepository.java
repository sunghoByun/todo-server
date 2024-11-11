package com.example.demo.persistence;

import com.example.demo.model.TodoDetailEntity;
import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoDetailRepository extends JpaRepository<TodoDetailEntity, String> {
    List<TodoDetailEntity> findAllByTodo(TodoEntity todo);


}
