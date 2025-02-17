package com.sarthak.chat_app.exceptions;

public class InvalidAuthenticationToken extends RuntimeException{
    public InvalidAuthenticationToken(String message) {
        super(message);
    }
}
