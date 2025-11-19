package com.example.hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataNotFoundException extends RuntimeException {
    private String message;
}