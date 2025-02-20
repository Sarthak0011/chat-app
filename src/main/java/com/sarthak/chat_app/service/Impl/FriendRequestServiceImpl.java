package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.dto.FriendRequestDto;
import com.sarthak.chat_app.entity.FriendRequestEntity;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.enums.RequestStatus;
import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.repository.FriendRequestRepository;
import com.sarthak.chat_app.repository.UserRepository;
import com.sarthak.chat_app.service.FriendRequestService;
import com.sarthak.chat_app.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public void sendFriendRequest(Long receiverId) {
        UserEntity sender = utils.getAuthenticatedUser();

        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));


        Optional<FriendRequestEntity> dbFriendRequest = friendRequestRepository.findBySenderIdAndReceiverId(sender.getId(), receiverId);
        if (dbFriendRequest.isPresent()) {
            throw new BadRequest("Friend request has been already sent.");
        }

        Optional<FriendRequestEntity> dbReverseFriendRequest = friendRequestRepository.findBySenderIdAndReceiverId(receiverId, sender.getId());
        if (dbReverseFriendRequest.isPresent()) {
            throw new BadRequest(receiver.getUsername() + " already sent a friend request to you.");
        }


        FriendRequestEntity newFriendRequest = new FriendRequestEntity();
        newFriendRequest.setSender(sender);
        newFriendRequest.setReceiver(receiver);
        FriendRequestEntity friendRequestId = friendRequestRepository.save(newFriendRequest);
    }

    @Override
    public List<FriendRequestDto> getFriendRequests() {
        UserEntity user = utils.getAuthenticatedUser();
        List<FriendRequestEntity> friendRequests = friendRequestRepository.findByReceiverAndRequestStatus(user, RequestStatus.PENDING);
        return friendRequests
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptFriendRequest(Long senderId) {
        UserEntity receiver = utils.getAuthenticatedUser();
        Long receiverId = receiver.getId();

        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        FriendRequestEntity friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        if(friendRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)) {
            throw new BadRequest("You both are already friends.");
        }

        Optional<FriendRequestEntity> reverseFriendRequest = friendRequestRepository.findBySenderIdAndReceiverId(receiverId, senderId);
        if(reverseFriendRequest.isPresent() && reverseFriendRequest.get().getRequestStatus().equals(RequestStatus.ACCEPTED)){
            throw new BadRequest("You both are already friends.");
        }


        friendRequest.setRequestStatus(RequestStatus.ACCEPTED);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void rejectFriendRequest(Long senderId) {
        UserEntity user = utils.getAuthenticatedUser();
        Long receiverId = user.getId();
        FriendRequestEntity friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        friendRequestRepository.delete(friendRequest);
    }

    @Override
    public List<FriendRequestDto> getSentFriendRequests() {
        UserEntity user = utils.getAuthenticatedUser();
        List<FriendRequestEntity> friendRequests = friendRequestRepository.findBySenderAndRequestStatus(user, RequestStatus.PENDING);
        return friendRequests
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private FriendRequestDto convertToDto(FriendRequestEntity friendRequestEntity) {
        FriendRequestDto friendRequestDto = new FriendRequestDto();
        friendRequestDto.setId(friendRequestEntity.getId());
        friendRequestDto.setSenderId(friendRequestEntity.getSender().getId());
        friendRequestDto.setReceiverId(friendRequestEntity.getReceiver().getId());
        return friendRequestDto;
    }
}
