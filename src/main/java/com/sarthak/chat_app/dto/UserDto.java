package com.sarthak.chat_app.dto;

import com.sarthak.chat_app.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
    private Long id;
    private String email;
    private String username;
    private Status status;
}
