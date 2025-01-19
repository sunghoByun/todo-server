// TodoShared Entity
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TodoShared")
public class TodoSharedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String todoId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String sharedBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String createdAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
