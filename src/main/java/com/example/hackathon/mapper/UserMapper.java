package com.example.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hackathon.dto.UserProfile;
import com.example.hackathon.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfile toDTO(UserEntity entity);
}