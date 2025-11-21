package com.example.hackathon.repository;


import com.example.hackathon.entity.ShareEntity;
import com.example.hackathon.entity.UserTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends JpaRepository<ShareEntity, Long> {

    @Query("SELECT s FROM ShareEntity s WHERE s.user.id = ?1")
    List<ShareEntity> findByUserId(Long userId);

    Optional<ShareEntity> findByTrip_Id(Long tripId);
}
