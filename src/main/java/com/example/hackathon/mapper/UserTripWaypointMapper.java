package com.example.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.hackathon.dto.UserTripWaypointRequest;
import com.example.hackathon.dto.UserTripWaypointResponse;
import com.example.hackathon.entity.UserTripWaypoint;

@Mapper(componentModel = "spring")
public interface UserTripWaypointMapper {
    UserTripWaypointResponse toDTO(UserTripWaypoint entity);
    void update(UserTripWaypointRequest dto, @MappingTarget UserTripWaypoint entity);
}
