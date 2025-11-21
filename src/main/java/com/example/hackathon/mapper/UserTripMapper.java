package com.example.hackathon.mapper;

import org.mapstruct.*;

import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.entity.UserTrip;

@Mapper(componentModel = "spring", uses = { UserTripWaypointMapper.class })
public interface UserTripMapper {

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserTripResponse toDTO(UserTrip entity);

    void update(UserTripRequest dto, @MappingTarget UserTrip entity);
}