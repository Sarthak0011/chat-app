package com.sarthak.chat_app.utils;

import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Utils {

    private final UserRepository userRepository;

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            Optional<UserEntity> user = userRepository.findByEmail(userDetails.getUsername());
            if(user.isPresent()) {
                return user.get();
            }
        }
        throw new UsernameNotFoundException("User not found");
    }
}
