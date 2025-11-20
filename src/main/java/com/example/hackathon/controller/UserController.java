package com.example.hackathon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.UserProfile;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.security.CustomUserDetail;
import com.example.hackathon.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(Authentication authentication) {
        UserEntity user = ((CustomUserDetail) authentication.getPrincipal()).getUserEntity();
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }
}
