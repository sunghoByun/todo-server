package com.example.demo.dto;

import com.example.demo.model.TodoDetailEntity;
import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDetailDTO {
    private String todoId;
    private int detailId;
    private String description;
    private LocalDate deadline;

    public TodoDetailDTO(final TodoDetailEntity entity) {
        this.detailId = entity.getDetailId();
        this.description = entity.getDescription();
        this.deadline = entity.getDeadline();
        this.todoId = entity.getTodo() != null ? entity.getTodo().getId() : null;
    }

    public static TodoDetailEntity toEntity(final TodoDetailDTO dto, final TodoEntity todoEntity) {
        return TodoDetailEntity.builder()
                .detailId(dto.getDetailId())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .todo(todoEntity)  // 외부에서 todoEntity를 전달받아 설정
                .build();
    }
}
