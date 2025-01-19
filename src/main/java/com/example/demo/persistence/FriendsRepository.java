package com.example.demo.persistence;
import com.example.demo.model.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<FriendEntity, Long> {

    Optional<FriendEntity> findByUserIdAndFriendId(String userId, String friendId);

    List<FriendEntity> findByUserIdOrFriendId(String userId, String friendId);
}
