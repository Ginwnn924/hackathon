package com.example.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.hackathon.dto.UserTripWaypointRequest;
import com.example.hackathon.dto.UserTripWaypointResponse;
import com.example.hackathon.entity.UserTripWaypoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTripWaypointMapper {
    UserTripWaypointResponse toDTO(UserTripWaypoint entity);
    void update(UserTripWaypointRequest dto, @MappingTarget UserTripWaypoint entity);
}
