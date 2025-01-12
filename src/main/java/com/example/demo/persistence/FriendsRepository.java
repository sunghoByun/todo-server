package com.example.demo.persistence;

import com.example.demo.model.FriendEntity;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<FriendEntity, String> {

    // 친구 요청을 보낸 목록 조회
    List<FriendEntity> findByFromUserId(String fromUserId);

    // 친구 요청을 받은 목록 조회
    List<FriendEntity> findByToUserId(String toUserId);

    // 두 사용자 간의 친구 관계 조회
    Optional<FriendEntity> findByFromUserIdAndToUserId(String fromUserId, String toUserId);

    boolean existsByFromUserIdAndToUserId(String fromUserId, String toUserId);

}
