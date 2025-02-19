package com.sarthak.chat_app.dto;

import lombok.Data;

@Data
public class FriendRequestDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
}
