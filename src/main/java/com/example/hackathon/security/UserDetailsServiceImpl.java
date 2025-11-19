package com.example.hackathon.security;

import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.exception.DataNotFoundException;
import com.example.hackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(username)
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));
        return new CustomUserDetail(entity);
    }

}