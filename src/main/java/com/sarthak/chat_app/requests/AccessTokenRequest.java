package com.sarthak.chat_app.requests;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String refreshToken;
}
