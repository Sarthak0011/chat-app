package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
