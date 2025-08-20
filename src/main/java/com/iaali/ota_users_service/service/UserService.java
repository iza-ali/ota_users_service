package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.UserProfileCombinedRequestDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getById(Long id);

    UserResponseDTO getByEmail(String email);

    Long getAssociatedProfileId(Long id);

    List<UserResponseDTO> getAll();

    UserProfileCombinedResponseDTO save(UserProfileCombinedRequestDTO dto);

    UserResponseDTO updatePassword(Long id, String password);

    UserResponseDTO updateEmail(Long id, String email);

    void softDelete(Long id);

    void hardDelete(Long id);
}
