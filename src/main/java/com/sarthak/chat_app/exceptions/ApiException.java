package com.sarthak.chat_app.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
