package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(UserEntity userEntity);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "passwordHash")
    @Mapping(target = "role", constant = "User")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    UserEntity toEntity(UserRequestDTO userDto);
}