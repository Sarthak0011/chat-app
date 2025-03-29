package com.sarthak.chat_app.service;

import com.sarthak.chat_app.dto.MessageDto;
import com.sarthak.chat_app.entity.MessageEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {

    MessageDto convertToDto(MessageEntity message);

    MessageEntity saveMessage(MessageEntity message);

    List<MessageDto> getMessages(Long conversationId, Pageable pageable);
}
