package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.dto.MessageDto;
import com.sarthak.chat_app.entity.MessageEntity;
import com.sarthak.chat_app.repository.MessageRepository;
import com.sarthak.chat_app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageDto convertToDto(MessageEntity message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setConversationId(message.getConversationEntity().getId());
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setContent(message.getContent());
        return messageDto;
    }

    @Override
    public MessageEntity saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getMessages(Long conversationId, Pageable pageable){
        List<MessageEntity> messages = messageRepository.getMessages(conversationId, pageable);
        return messages.stream().map(this::convertToDto).toList();
    }
}
