package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TodoDetail")
public class TodoDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;

    private String description;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private TodoEntity todo;
}
