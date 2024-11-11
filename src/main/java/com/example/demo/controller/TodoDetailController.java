package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.dto.TodoDetailDTO;
import com.example.demo.model.TodoDetailEntity;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoDetailService;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo/{todoId}")
public class TodoDetailController {

    @Autowired
    private TodoDetailService todoDetailService;

    @Autowired
    private TodoService todoService;

    @PostMapping("/details")
    public ResponseEntity<?> createTodoDetail(@AuthenticationPrincipal String userId, @PathVariable("todoId") String todoId, @RequestBody TodoDetailDTO dto) {
        try {

            Optional<TodoEntity> todoEntity = todoService.getTodo(todoId);

            if (todoEntity.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
            }

            TodoDetailEntity entity = TodoDetailDTO.toEntity(dto, todoEntity.get());

            List<TodoDetailEntity> entities = todoDetailService.create(entity);

            // 반환된 엔티티 리스트를 DTO 리스트로 변환
            List<TodoDetailDTO> dtos = entities.stream().map(TodoDetailDTO::new).collect(Collectors.toList());

            // DTO 리스트를 ResponseDTO 형태로 전환
            ResponseDTO<TodoDetailDTO> response = ResponseDTO.<TodoDetailDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ResponseDTO<TodoDetailDTO> response = ResponseDTO.<TodoDetailDTO>builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(response);
        }

    }
}


