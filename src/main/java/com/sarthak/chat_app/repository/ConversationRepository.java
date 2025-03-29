package com.sarthak.chat_app.repository;

import com.sarthak.chat_app.entity.ConversationEntity;
import com.sarthak.chat_app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {

    Optional<ConversationEntity> findByUser1AndUser2(UserEntity user1, UserEntity user2);
}
