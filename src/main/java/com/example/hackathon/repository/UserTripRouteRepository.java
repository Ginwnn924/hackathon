package com.example.hackathon.repository;

import com.example.hackathon.entity.UserTripRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTripRouteRepository extends JpaRepository<UserTripRoute, Long> {
    List<UserTripRoute> findByUserTripId(Long tripId);
}