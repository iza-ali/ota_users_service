package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getById(Long id);

    UserResponseDTO getByEmail(String email);

    List<UserResponseDTO> getAll();

    ProfileResponseDTO getAssociatedProfile(Long id);

    UserResponseDTO save(UserRequestDTO user);

    UserResponseDTO updatePassword(Long id, String password);

    UserResponseDTO updateEmail(Long id, String email);

    void softDelete(Long id);

    void hardDelete(Long id);
}
