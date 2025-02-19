package com.sarthak.chat_app.service;

import com.sarthak.chat_app.dto.FriendRequestDto;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.enums.RequestStatus;
import com.sarthak.chat_app.requests.FriendRequestRequest;

import java.util.List;

public interface FriendRequestService {

    void sendFriendRequest(FriendRequestRequest friendRequestRequest);

    List<FriendRequestDto> getFriendRequests();

    void acceptFriendRequest(Long senderId);

    void rejectFriendRequest(Long senderId);
}
