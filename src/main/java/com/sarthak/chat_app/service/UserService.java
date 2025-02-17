package com.sarthak.chat_app.service;

import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.requests.LoginUserRequest;
import com.sarthak.chat_app.requests.RegisterUserRequest;

import java.util.Map;

public interface UserService {

    UserDto createUser(RegisterUserRequest registerUserRequest);

    Map<String, String> loginUser(LoginUserRequest loginUserRequest);

    Map<String, String> generateAccessToken(String refreshToken);

    UserDto getUser(Long userId);
}
