package com.example.demo.controller;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.FriendEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.service.FriendService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @PostMapping
    public ResponseEntity<?> requestFriends(@AuthenticationPrincipal String userId, @RequestBody UserDTO toUserDTO) {
        try {
            UserEntity toUser = UserEntity.builder()
                    .email(toUserDTO.getEmail())
                    .build();

            UserEntity addUser = userService.getByUserEmail(toUser.getEmail());
            UserEntity fromUser = userService.getByUserId(userId);
            //친구 추가 서비스
//            FriendEntity fromFriend = FriendEntity.builder()
//                    .fromUserId(fromUser.getId())
//                    .toUserId(addUser.getId())
//                    .done(true)
//                    .build();
//
//            FriendEntity toFriend = FriendEntity.builder()
//                    .fromUserId(addUser.getId())
//                    .toUserId(fromUser.getId())
//                    .done(false)
//                    .build();

            FriendEntity res = friendService.create(fromUser.getId(), addUser.getId());

            return ResponseEntity.ok("Friend request successfully created");
//            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {

            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
