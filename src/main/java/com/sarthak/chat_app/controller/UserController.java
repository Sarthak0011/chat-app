package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.exceptions.ApiException;
import com.sarthak.chat_app.exceptions.InvalidAuthenticationToken;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;



    @GetMapping("/get-user/{userId}")
    public UserDto authTest(@PathVariable Long userId){
        UserDto userDto = userService.getUser(userId);
        return userDto;
    }


}
