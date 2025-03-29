package com.sarthak.chat_app.service;

import com.sarthak.chat_app.entity.ConversationEntity;
import com.sarthak.chat_app.entity.UserEntity;

public interface ConversationService {

    ConversationEntity getConversationById(Long conversationId);

    ConversationEntity getConversation(UserEntity user1, UserEntity user2);
}
