package com.example.hackathon.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private String fullName;
    private String accessToken;
    private String refreshToken;

}
