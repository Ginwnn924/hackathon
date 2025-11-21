package com.example.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
    private Long id;
    private String name;
    private String email;
    private String img;
}
