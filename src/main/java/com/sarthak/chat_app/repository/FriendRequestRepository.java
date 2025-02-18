package com.sarthak.chat_app.repository;

import com.sarthak.chat_app.entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {

    List<FriendRequestEntity> findBySenderId(Long senderId);

    List<FriendRequestEntity> findByReceiverId(Long receiverId);

    Optional<FriendRequestEntity> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
