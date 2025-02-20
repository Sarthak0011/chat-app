package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.FriendRequestDto;
import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.responses.ApiResponse;
import com.sarthak.chat_app.service.FriendRequestService;
import com.sarthak.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final FriendRequestService friendRequestService;


    @GetMapping("/get-user/{userId}")
    public UserDto authTest(@PathVariable Long userId){
        UserDto userDto = userService.getUser(userId);
        return userDto;
    }

    @PostMapping("/friend-request/send")
    public ResponseEntity<ApiResponse> sendFriendRequest(@RequestParam Long receiverId) {
        friendRequestService.sendFriendRequest(receiverId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, null, "Friend request send successfully!", ""));
    }

    @PostMapping("/friend-request/accept")
    public ResponseEntity<ApiResponse> acceptFriendRequest(@RequestParam Long senderId) {
        friendRequestService.acceptFriendRequest(senderId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, null, "Friend request accepted", ""));
    }

    @PostMapping("/friend-request/reject")
    public ResponseEntity<ApiResponse> rejectFriendRequest(@RequestParam Long senderId) {
        friendRequestService.rejectFriendRequest(senderId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, null, "Friend request has been deleted", ""));
    }


    @GetMapping("/friend-requests/get")
    public ResponseEntity<ApiResponse> getFriendRequests() {
        List<FriendRequestDto> friendRequestDtos = friendRequestService.getFriendRequests();
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, friendRequestDtos, "Friend Requests Fetched", ""));
    }

    @GetMapping("/friend-requests/sent")
    public ResponseEntity<ApiResponse> getSentFriendRequests() {
        List<FriendRequestDto> friendRequestDtos = friendRequestService.getSentFriendRequests();
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, friendRequestDtos, "Sent friend requests fetched.", ""));
    }


}
