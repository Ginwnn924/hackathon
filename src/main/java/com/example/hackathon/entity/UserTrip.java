package com.example.hackathon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_trip")
@Getter
@Setter
public class UserTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "total_distance")
    private Double totalDistance;

    @Column(name = "total_duration")
    private Double totalDuration;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @OneToMany(mappedBy = "userTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTripWaypoint> waypoints;
}