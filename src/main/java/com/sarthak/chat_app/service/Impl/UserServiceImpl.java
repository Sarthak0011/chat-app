package com.sarthak.chat_app.service.Impl;

import com.sarthak.chat_app.config.security.JwtUtils;
import com.sarthak.chat_app.config.security.UserDetailsServiceImpl;
import com.sarthak.chat_app.dto.UserDto;
import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.exceptions.ApiException;
import com.sarthak.chat_app.exceptions.InvalidAuthenticationToken;
import com.sarthak.chat_app.exceptions.ResourceNotFoundException;
import com.sarthak.chat_app.repository.UserRepository;
import com.sarthak.chat_app.requests.LoginUserRequest;
import com.sarthak.chat_app.requests.RegisterUserRequest;
import com.sarthak.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public UserDto createUser(RegisterUserRequest registerUserRequest) {
        if (!registerUserRequest.getPassword().equals(registerUserRequest.getConfirmPassword())) {
            throw new ApiException("Passwords doesn't match.");
        }
        Optional<UserEntity> dbUser = userRepository.findByEmail(registerUserRequest.getEmail());
        if (dbUser.isPresent()) {
            throw new ApiException("User already registered.");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerUserRequest.getEmail());
        userEntity.setUsername(registerUserRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setUsername(savedUser.getUsername());

        return userDto;
    }

    @Override
    public Map<String, String> loginUser(LoginUserRequest loginUserRequest) {
        String email = loginUserRequest.getEmail();
        String password = loginUserRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String refreshToken = jwtUtils.generateToken(email, false);
        String accessToken = jwtUtils.generateToken(email, true);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("refreshToken", refreshToken);
        tokens.put("accessToken", accessToken);
        return tokens;
    }

    @Override
    public Map<String, String> generateAccessToken(String refreshToken) {
        if(!jwtUtils.validateToken(refreshToken)){
            throw new InvalidAuthenticationToken("Invalid Token");
        }

        String email = jwtUtils.extractSubject(refreshToken);
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        String accessToken = jwtUtils.generateToken(email, true);
        HashMap<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        return token;
    }

    @Override
    public UserDto getUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        UserDto userDto = new UserDto();
        userDto.setId(user.get().getId());
        userDto.setUsername(user.get().getUsername());
        userDto.setEmail(user.get().getEmail());
        return userDto;
    }
}
