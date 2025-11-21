package com.example.hackathon.controller;

import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.entity.UserTrip;
import com.example.hackathon.service.UserTripService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.UserProfile;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.security.CustomUserDetail;
import com.example.hackathon.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserTripService userTripService;


    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(Authentication authentication) {
        UserEntity user = ((CustomUserDetail) authentication.getPrincipal()).getUserEntity();
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }

    @GetMapping("/{id}/trips")
    public ResponseEntity<List<UserTripResponse>> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userTripService.getTripsByUserId(id));
    }
}
