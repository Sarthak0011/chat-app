package com.sarthak.chat_app.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String content;
}
