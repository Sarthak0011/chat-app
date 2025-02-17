package com.sarthak.chat_app.requests;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
