package com.example.hackathon.security;

import com.example.hackathon.dto.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    AuthResponse generateToken(UserDetails user);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractEmail(String token);

    boolean isValidateToken(String token, UserDetails userDetails);

    Date extractExpiration(String token);

    Long getTokenExpire(String token);

}