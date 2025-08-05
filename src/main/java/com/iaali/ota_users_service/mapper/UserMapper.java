package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.UserDTO;
import com.iaali.ota_users_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", constant = "User")
    User toEntity(UserDTO userDto);
}
