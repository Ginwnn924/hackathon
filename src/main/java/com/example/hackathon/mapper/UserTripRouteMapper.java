package com.example.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.hackathon.dto.UserTripRouteRequest;
import com.example.hackathon.dto.UserTripRouteResponse;
import com.example.hackathon.entity.UserTripRoute;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTripRouteMapper {
    UserTripRouteResponse toDTO(UserTripRoute entity);
    void update(UserTripRouteRequest dto, @MappingTarget UserTripRoute entity);
}
