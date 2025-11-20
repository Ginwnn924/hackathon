package com.example.hackathon.service;

import org.springframework.stereotype.Service;

import com.example.hackathon.dto.UserProfile;
import com.example.hackathon.mapper.UserMapper;
import com.example.hackathon.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfile getUserProfile(Long userId) {
        UserProfile user = userRepository.findById(userId)
                .map(UserMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

}
