package com.sarthak.chat_app.repository;

import com.sarthak.chat_app.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> getMessages(Long conversationId, Pageable pageable);
}
