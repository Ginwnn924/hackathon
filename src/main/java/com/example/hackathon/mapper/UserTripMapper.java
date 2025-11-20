package com.example.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.entity.UserTrip;

@Mapper(componentModel = "spring", uses = { UserTripRouteMapper.class,
        UserTripWaypointMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTripMapper {

    UserTripResponse toDTO(UserTrip entity);
    void update(UserTripRequest dto, @MappingTarget UserTrip entity);
}