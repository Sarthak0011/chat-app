package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.dto.FriendRequestDto;
import com.sarthak.chat_app.entity.FriendRequestEntity;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.enums.RequestStatus;
import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.repository.FriendRequestRepository;
import com.sarthak.chat_app.repository.UserRepository;
import com.sarthak.chat_app.requests.FriendRequestRequest;
import com.sarthak.chat_app.service.FriendRequestService;
import com.sarthak.chat_app.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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
    public void sendFriendRequest(FriendRequestRequest friendRequestRequest) {
        Long senderId = friendRequestRequest.getSenderId();
        Long receiverId = friendRequestRequest.getReceiverId();
        Optional<FriendRequestEntity> dbFriendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (dbFriendRequest.isPresent()) {
            throw new BadRequest("Friend request has been already sent.");
        }
        FriendRequestEntity newFriendRequest = new FriendRequestEntity();
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found."));

        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found."));

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
        UserEntity user = utils.getAuthenticatedUser();
        Long receiverId = user.getId();
        FriendRequestEntity friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        if(friendRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)) {
            throw new BadRequest("Request already been accepted");
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


    private FriendRequestDto convertToDto(FriendRequestEntity friendRequestEntity) {
        FriendRequestDto friendRequestDto = new FriendRequestDto();
        friendRequestDto.setId(friendRequestEntity.getId());
        friendRequestDto.setSenderId(friendRequestEntity.getSender().getId());
        friendRequestDto.setReceiverId(friendRequestEntity.getReceiver().getId());
        return friendRequestDto;
    }
}
