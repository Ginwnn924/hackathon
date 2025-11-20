package com.example.hackathon.repository;

import com.example.hackathon.entity.UserTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTripRepository extends JpaRepository<UserTrip, Long> {
    List<UserTrip> findByUserId(Long userId);
}