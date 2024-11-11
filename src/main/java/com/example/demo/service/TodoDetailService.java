package com.example.demo.service;

import com.example.demo.model.TodoDetailEntity;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoDetailRepository;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoDetailService {

    @Autowired
    private TodoDetailRepository repository;

    public List<TodoDetailEntity> create(final TodoDetailEntity entity) {

        repository.save(entity);
        log.info("Entity is created : " + entity.toString());

        return repository.findAllByTodo(entity.getTodo());
    }
}
