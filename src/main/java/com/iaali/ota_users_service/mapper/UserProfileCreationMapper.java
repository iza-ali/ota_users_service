package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.UserProfileCombinedRequestDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.entity.UserEntity;

public interface UserProfileCreationMapper {

    UserEntity toUserEntity(UserProfileCombinedRequestDTO dto);

    ProfileEntity toProfileEntity(UserProfileCombinedRequestDTO dto);

    UserProfileCombinedResponseDTO toUserProfileResponse(UserEntity user, ProfileEntity profile);
}
