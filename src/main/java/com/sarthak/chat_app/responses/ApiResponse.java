package com.sarthak.chat_app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    Boolean success;
    Object data;
    String message = "";
    String error= "";
}
