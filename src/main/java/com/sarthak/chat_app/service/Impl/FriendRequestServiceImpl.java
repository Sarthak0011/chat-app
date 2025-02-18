package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.entity.FriendRequestEntity;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.repository.FriendRequestRepository;
import com.sarthak.chat_app.repository.UserRepository;
import com.sarthak.chat_app.requests.FriendRequestRequest;
import com.sarthak.chat_app.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Override
    public void sendFriendRequest(FriendRequestRequest friendRequestRequest) {
        Long senderId = friendRequestRequest.getSenderId();
        Long receiverId = friendRequestRequest.getReceiverId();
        Optional<FriendRequestEntity> dbFriendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (dbFriendRequest.isPresent()) {
            throw new BadRequest("Friend request has been already sent.");
        }
        FriendRequestEntity newFriendRequest = new FriendRequestEntity();
        Optional<UserEntity> sender = userRepository.findById(senderId);
        Optional<UserEntity> receiver = userRepository.findById(receiverId);

        if (sender.isEmpty()) {
            throw new ResourceNotFoundException("Sender not found.");
        }
        if (receiver.isEmpty()) {
            throw new ResourceNotFoundException("Receiver not found.");
        }

        newFriendRequest.setSender(sender.get());
        newFriendRequest.setReceiver(receiver.get());
        FriendRequestEntity friendRequestId = friendRequestRepository.save(newFriendRequest);
    }
}
