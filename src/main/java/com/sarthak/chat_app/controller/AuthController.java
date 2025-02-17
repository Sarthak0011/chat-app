package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.config.security.JwtUtils;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        try {
            Map<String, String> tokens = userService.loginUser(loginUserRequest);
            return ResponseEntity
                    .status(OK)
                    .body(new ApiResponse(true, tokens, "Login Successful", ""));
        }
        catch (UsernameNotFoundException e) {
            logger.warn("User not found -> {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(false, null, "User not found", e.getMessage()));
        }
        catch (AuthenticationException e){
            logger.warn("Unauthorized request -> {}", e.getMessage());
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(new ApiResponse(false, null, "Invalid Credentials", e.getMessage()));
        }
        catch (Exception e) {
            logger.error("Something went wrong -> {}", e.getMessage());
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, null, "Something went wrong", e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            UserDto userDto = userService.createUser(registerUserRequest);
            return ResponseEntity
                    .status(OK)
                    .body(new ApiResponse(true,userDto, "User created", ""));
        }
        catch(ResourceNotFoundException e){
            logger.warn("User not found {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(false, null, "User not found", e.getMessage()));
        }
        catch (ApiException e) {
            logger.warn("Bad request {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ApiResponse(false, null, "Bad request", e.getMessage()));
        }
        catch (Exception e){
            logger.error("Something went wrong -> {}", e.getMessage());
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false,null,"Something went wrong", e.getMessage()));
        }
    }

    @PostMapping("/access-token")
    public ResponseEntity<ApiResponse> generateAccessToken(@RequestBody AccessTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            Map<String, String> token = userService.generateAccessToken(refreshToken);
            return ResponseEntity
                    .status(OK)
                    .body(new ApiResponse(true, token, "Access token generated", ""));
        }
        catch (InvalidAuthenticationToken e) {
            logger.warn("Invalid Token -> {}", e.getMessage());
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(new ApiResponse(false, null, "Invalid Token", e.getMessage()));
        }
        catch (ResourceNotFoundException e) {
            logger.warn("User not found {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(false, null, "User not found", e.getMessage()));
        }
        catch (ApiException e) {
            logger.warn("Bad request {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ApiResponse(false, null, "Bad request", e.getMessage()));
        }
        catch (Exception e){
            logger.error("Something went wrong -> {}", e.getMessage());
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false,null,"Something went wrong", e.getMessage()));
        }
    }

}
