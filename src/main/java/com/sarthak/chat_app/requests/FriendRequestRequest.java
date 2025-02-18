package com.sarthak.chat_app.requests;

import lombok.Data;

@Data
public class FriendRequestRequest {
    private Long senderId;
    private Long receiverId;
}
