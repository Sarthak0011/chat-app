package com.sarthak.chat_app.requests;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
