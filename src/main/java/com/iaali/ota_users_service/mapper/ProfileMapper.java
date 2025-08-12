package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.ProfileRequestDTO;
import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponseDTO toDTO(ProfileEntity profileEntity);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    ProfileEntity toEntity(ProfileRequestDTO profileDto);
}