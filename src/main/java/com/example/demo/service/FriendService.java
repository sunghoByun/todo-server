package com.example.demo.service;

import com.example.demo.dto.FriendDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.FriendEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.FriendsRepository;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FriendService {


    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseDTO<String> sendFriendRequest(FriendDTO friendsRequestDTO) {
        UserEntity user = userRepository.findById(friendsRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity friend = userRepository.findById(friendsRequestDTO.getFriendId())
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        // 이미 친구 요청이 존재하는지 확인
        if (friendsRepository.findByUserIdAndFriendId(user.getId(), friend.getId()).isPresent()) {
            return new ResponseDTO<>("error", List.of("Friend request already exists"));

        }

        FriendEntity newFriendRequest = FriendEntity.builder()
                .user(user)
                .friend(friend)
                .status(FriendEntity.FriendStatus.PENDING)
                .build();

        friendsRepository.save(newFriendRequest);
        return new ResponseDTO<>(null, List.of("Friend request sent successfully"));
    }

    public ResponseDTO<?> getFriends(String userId) {
        List<FriendEntity> friendsList = friendsRepository.findByUserIdOrFriendId(userId, userId);
        List<String> friends = friendsList.stream()
                .map(friend -> friend.getUser().getId().equals(userId) ? friend.getFriend().getId() : friend.getUser().getId())
                .collect(Collectors.toList());

        return ResponseDTO.<String>builder().data(friends).build();

    }

    public ResponseDTO<String> acceptFriendRequest(FriendDTO friendsRequestDTO) {
        FriendEntity friendRequest = friendsRepository.findByUserIdAndFriendId(friendsRequestDTO.getFriendId(), friendsRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (friendRequest.getStatus() == FriendEntity.FriendStatus.PENDING) {
            friendRequest.setStatus(FriendEntity.FriendStatus.ACCEPTED);
            friendsRepository.save(friendRequest);
            return new ResponseDTO<>(null, List.of("Friend request accepted"));
        } else {
            return new ResponseDTO<>("error", List.of("Friend request already processed"));
        }
    }


//    @Transactional
//    public FriendEntity create(String fromUserId, String toUserId) {
//        if (fromUserId == null || toUserId == null) {
//            throw new IllegalArgumentException("Invalid arguments");
//        }
//
//        // 중복 친구 요청 방지
//        if (friendsRepository.existsByFromUserIdAndToUserId(fromUserId, toUserId)) {
//            throw new IllegalArgumentException("Friend relationship already exists.");
//        }
//
//        FriendEntity fromFriend = createFriendEntity(fromUserId, toUserId, true);
//        FriendEntity toFriend = createFriendEntity(toUserId, fromUserId, false);
//
//        // 저장
//        FriendEntity res = friendsRepository.save(fromFriend);
//        friendsRepository.save(toFriend);
//
//        log.info("Friends created between {} and {}", fromUserId, toUserId);
//
//        return res;
//    }
//
//
//    private FriendEntity createFriendEntity(String fromUserId, String toUserId, boolean areWeFriend) {
//        return FriendEntity.builder()
//                .fromUserId(fromUserId)
//                .toUserId(toUserId)
//                .areWeFriend(areWeFriend)
//                .build();
//    }
}
