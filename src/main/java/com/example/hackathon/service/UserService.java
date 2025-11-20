package com.example.hackathon.service;

import org.springframework.stereotype.Service;

import com.example.hackathon.dto.UserProfile;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfile getUserProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getName());
        userProfile.setEmail(user.getEmail());
        userProfile.setImg(user.getImg());
        return userProfile;
    }

}
