package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.MessageDto;
import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.entity.ConversationEntity;
import com.sarthak.chat_app.entity.MessageEntity;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.enums.Status;
import com.sarthak.chat_app.service.ConversationService;
import com.sarthak.chat_app.service.MessageService;
import com.sarthak.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConversationService conversationService;
    private final UserService userService;
    private final MessageService messageService;

    @MessageMapping("/chat/{conversationId}/sendMessage")
    @SendTo("/topic/conversation/{conversationId}")
    public MessageDto sendMessage(@DestinationVariable Long conversationId,
                                  @Payload MessageDto messageDto) {
        ConversationEntity conversation = conversationService.getConversationById(conversationId);
        if(conversation == null) {
            return null;
        }

        UserEntity sender = userService.getUserEntity(messageDto.getSenderId());
        if(sender == null) {
            return null;
        }

        MessageEntity message = new MessageEntity();
        message.setConversationEntity(conversation);
        message.setSender(sender);
        message.setContent(messageDto.getContent());

        MessageEntity savedMessage = messageService.saveMessage(message);

        return messageService.convertToDto(savedMessage);
    }
}
