package com.example.demo.model;

import com.example.demo.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "friends")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FriendEntity.FriendId.class)
public class FriendEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;  // 요청한 사용자

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private UserEntity friend;  // 친구

    @Enumerated(EnumType.STRING)
    private FriendStatus status;  // 친구 요청 상태

    public enum FriendStatus {
        PENDING, ACCEPTED, REJECTED
    }

    // 복합 키 클래스를 지정하기 위한 inner 클래스
    @Embeddable
    public static class FriendId implements Serializable {
        private String userId;
        private String friendId;

        // 기본 생성자, equals(), hashCode() 메소드가 필요합니다.
        public FriendId() {}

        public FriendId(String userId, String friendId) {
            this.userId = userId;
            this.friendId = friendId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendId friendId1 = (FriendId) o;
            return userId.equals(friendId1.userId) && friendId.equals(friendId1.friendId);
        }

        @Override
        public int hashCode() {
            return 31 * userId.hashCode() + friendId.hashCode();
        }
    }
}
