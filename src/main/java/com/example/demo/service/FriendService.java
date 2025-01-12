package com.example.demo.service;

import com.example.demo.model.FriendEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.FriendsRepository;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FriendService {
    @Autowired
    private FriendsRepository friendsRepository;

    @Transactional
    public FriendEntity create(String fromUserId, String toUserId) {
        if (fromUserId == null || toUserId == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        // 중복 친구 요청 방지
        if (friendsRepository.existsByFromUserIdAndToUserId(fromUserId, toUserId)) {
            throw new IllegalArgumentException("Friend relationship already exists.");
        }

        FriendEntity fromFriend = createFriendEntity(fromUserId, toUserId, true);
        FriendEntity toFriend = createFriendEntity(toUserId, fromUserId, false);

        // 저장
        FriendEntity res = friendsRepository.save(fromFriend);
        friendsRepository.save(toFriend);

        log.info("Friends created between {} and {}", fromUserId, toUserId);

        return res;
    }


    private FriendEntity createFriendEntity(String fromUserId, String toUserId, boolean areWeFriend) {
        return FriendEntity.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .areWeFriend(areWeFriend)
                .build();
    }
}
