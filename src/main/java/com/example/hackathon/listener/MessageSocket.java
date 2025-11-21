package com.example.hackathon.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSocket {
    private Long id;
    private String name;
    private String code;
    private String img;
    private Double lat;
    private Double lng;
}
