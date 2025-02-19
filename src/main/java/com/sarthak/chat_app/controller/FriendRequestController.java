package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.FriendRequestDto;
import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.requests.FriendRequestRequest;
import com.sarthak.chat_app.responses.ApiResponse;
import com.sarthak.chat_app.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class FriendRequestController {

    private static final Logger logger = LoggerFactory.getLogger(FriendRequestController.class);

    private final FriendRequestService friendRequestService;

    @PostMapping("/friend-request/send")
    public ResponseEntity<ApiResponse> sendFriendRequest(@RequestBody
                                                         FriendRequestRequest friendRequestRequest) {

        friendRequestService.sendFriendRequest(friendRequestRequest);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, null, "Friend request send successfully!", ""));
    }

    @PostMapping("/friend-request/accept")
    public ResponseEntity<ApiResponse> acceptFriendRequest(@RequestBody Long senderId) {
        friendRequestService.acceptFriendRequest(senderId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, null, "Friend request accepted", ""));
    }

    @PostMapping("/friend-request/reject")
    public ResponseEntity<ApiResponse> rejectFriendRequest(@RequestBody Long senderId) {
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
}
