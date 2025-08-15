package com.iaali.ota_users_service.mapper.impl;

import com.iaali.ota_users_service.dto.UserProfileCombinedRequestDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.entity.Role;
import com.iaali.ota_users_service.entity.UserEntity;
import com.iaali.ota_users_service.mapper.UserProfileCreationMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProfileCreationMapperImpl implements UserProfileCreationMapper {

    @Override
    public UserEntity toUserEntity(UserProfileCombinedRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity user = new UserEntity();

        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword());
        user.setRole(Role.User);
        user.setDeleted(false);

        return user;
    }

    @Override
    public ProfileEntity toProfileEntity(UserProfileCombinedRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        ProfileEntity profile = new ProfileEntity();

        profile.setUsername(dto.getUsername());
        profile.setUser(null); // Added in the service
        profile.setBio(dto.getBio());
        profile.setAvatarUrl(dto.getAvatarUrl());
        profile.setDeleted(false);

        return profile;
    }

    @Override
    public UserProfileCombinedResponseDTO toUserProfileResponse(UserEntity user, ProfileEntity profile) {
        if (user == null || profile == null) {
            return null;
        }

        UserProfileCombinedResponseDTO response  = new UserProfileCombinedResponseDTO();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setUserCreatedAt(user.getCreatedAt());
        response.setUserUpdatedAt(user.getUpdatedAt());
        response.setProfileId(profile.getId());
        response.setUsername(profile.getUsername());
        response.setBio(profile.getBio());
        response.setAvatarUrl(profile.getAvatarUrl());
        response.setProfileCreatedAt(profile.getCreatedAt());
        response.setProfileUpdatedAt(profile.getUpdatedAt());

        return response;
    }
}
