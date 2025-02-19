package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.exceptions.BadRequest;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.requests.AccessTokenRequest;
import com.sarthak.chat_app.requests.LoginUserRequest;
import com.sarthak.chat_app.requests.RegisterUserRequest;
import com.sarthak.chat_app.responses.ApiResponse;
import com.sarthak.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        Map<String, String> tokens = userService.loginUser(loginUserRequest);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, tokens, "Login Successful", ""));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        UserDto userDto = userService.createUser(registerUserRequest);
        return ResponseEntity
                .status(CREATED)
                .body(new ApiResponse(true,userDto, "User created", ""));
    }

    @PostMapping("/access-token")
    public ResponseEntity<ApiResponse> generateAccessToken(@RequestBody AccessTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        Map<String, String> token = userService.generateAccessToken(refreshToken);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, token, "Access token generated", ""));
    }
}
