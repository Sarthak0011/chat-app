package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.requests.FriendRequestRequest;
import com.sarthak.chat_app.responses.ApiResponse;
import com.sarthak.chat_app.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
