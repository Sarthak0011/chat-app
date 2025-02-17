package com.sarthak.chat_app.config.security;

import com.sarthak.chat_app.entity.UserEntity;
import com.sarthak.chat_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails userDetails = new CustomUserDetails(user.get());
        return userDetails;
    }
}
