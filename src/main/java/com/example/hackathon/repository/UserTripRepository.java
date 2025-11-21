package com.example.hackathon.repository;

import com.example.hackathon.entity.UserTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTripRepository extends JpaRepository<UserTrip, Long> {

    Optional<UserTrip> findByCode(String code);

}