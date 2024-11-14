package com.example.demo.service;

import com.example.demo.model.TodoDetailEntity;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<TodoDetailEntity> getAll(final TodoEntity entity) {
        return repository.findAllByTodo(entity);
    }

    public Optional<TodoDetailEntity> get(final int detailId) {
        return repository.findById(detailId);
    }
    public Optional<TodoDetailEntity> update(final TodoDetailEntity entity) {
        Optional<TodoDetailEntity> original = repository.findById(entity.getDetailId());

        original.ifPresent(todoDetail -> {
                    todoDetail.setDeadline(entity.getDeadline());
                    todoDetail.setDescription(entity.getDescription());
                    repository.save(todoDetail);

                }
        );
        return repository.findById(entity.getDetailId());
    }

    public List<TodoDetailEntity> delete(final TodoDetailEntity entity) {
        repository.delete(entity);
        return this.getAll(entity.getTodo());
    }
}
