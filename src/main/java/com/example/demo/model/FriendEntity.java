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
@Table(name = "friends", uniqueConstraints = {@UniqueConstraint(columnNames = "friendId")})
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int friendId;

    @Column(nullable = false)
    private String fromUserId;

    @Column(nullable = false)
    private String toUserId;

    private boolean areWeFriend;
}
