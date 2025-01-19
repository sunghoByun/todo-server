package com.example.demo.controller;

import com.example.demo.dto.FriendDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    @Autowired
    private final FriendService friendsService;

    // 친구 요청 보내기
    @PostMapping("/request")
    public ResponseEntity<?>  sendFriendRequest(@AuthenticationPrincipal String userId, @RequestBody FriendDTO friendsRequestDTO) {
        // AuthenticationPrincipal로 인증된 사용자의 userId를 가져와서 friendsRequestDTO에 설정
        friendsRequestDTO.setUserId(userId);
        ResponseDTO<String> response = friendsService.sendFriendRequest(friendsRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 친구 리스트 조회
    @GetMapping
    public ResponseEntity<?>  getFriends(@AuthenticationPrincipal String userId) {
        // AuthenticationPrincipal로 인증된 사용자의 userId를 가져와서 서비스에 전달
        return ResponseEntity.ok().body(friendsService.getFriends(userId));
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendRequest(@AuthenticationPrincipal String userId, @RequestBody FriendDTO friendsRequestDTO) {
        // AuthenticationPrincipal로 인증된 사용자의 userId를 가져와서 friendsRequestDTO에 설정
        friendsRequestDTO.setUserId(userId);
        return ResponseEntity.ok(friendsService.acceptFriendRequest(friendsRequestDTO));
    }
}
