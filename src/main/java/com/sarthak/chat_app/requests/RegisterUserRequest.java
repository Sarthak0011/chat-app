package com.sarthak.chat_app.requests;

import com.sarthak.chat_app.enums.Status;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private Status status;
}
