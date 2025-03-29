package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.entity.ConversationEntity;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.repository.ConversationRepository;
import com.sarthak.chat_app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Override
    public ConversationEntity getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found."));
    }

    @Override
    public ConversationEntity getConversation(UserEntity user1, UserEntity user2) {
        ConversationEntity existingConversation = getConversationBetweenUsers(user1, user2);
        if(existingConversation == null) {
            ConversationEntity newConversation = new ConversationEntity();
            newConversation.setUser1(user1);
            newConversation.setUser2(user2);
            return newConversation;
        }
        return existingConversation;
    }

    public ConversationEntity getConversationBetweenUsers(UserEntity user1, UserEntity user2) {
        Optional<ConversationEntity> conversation = conversationRepository.findByUser1AndUser2(user1, user2);
        if(conversation.isPresent()) {
            return conversation.get();
        }
        conversation = conversationRepository.findByUser1AndUser2(user2, user1);
        return conversation.orElse(null);
    }
}
