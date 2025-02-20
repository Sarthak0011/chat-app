package com.sarthak.chat_app.service;

import com.sarthak.chat_app.dto.FriendRequestDto;

import java.util.List;

public interface FriendRequestService {

    void sendFriendRequest(Long receiverId);

    List<FriendRequestDto> getFriendRequests();

    void acceptFriendRequest(Long senderId);

    void rejectFriendRequest(Long senderId);

    List<FriendRequestDto> getSentFriendRequests();
}
