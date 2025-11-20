package com.example.hackathon.repository;

import com.example.hackathon.entity.UserTripWaypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTripWaypointRepository extends JpaRepository<UserTripWaypoint, Long> {
    List<UserTripWaypoint> findByUserTripId(Long tripId);
}