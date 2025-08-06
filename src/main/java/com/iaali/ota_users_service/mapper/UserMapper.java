package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "passwordHash")
    @Mapping(target = "role", constant = "User")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toEntity(UserRequestDTO userDto);
}