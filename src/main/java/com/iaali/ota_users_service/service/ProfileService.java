package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;

import java.util.List;

public interface ProfileService {

    ProfileResponseDTO getById(Long id);

    ProfileEntity getEntityById(Long id);

    ProfileResponseDTO getByUsername(String username);

    ProfileResponseDTO getByUserId(Long id);

    UserResponseDTO getAssociatedUser(Long id);

    List<ProfileResponseDTO> getAll();

    ProfileEntity save(ProfileEntity profile);

    ProfileResponseDTO updateUsername(Long id, String username);

    ProfileResponseDTO updateBio(Long id, String bio);

    ProfileResponseDTO updateAvatar(Long id, String avatarUrl);

    void softDelete(Long id);

    void hardDelete(Long id);
}
