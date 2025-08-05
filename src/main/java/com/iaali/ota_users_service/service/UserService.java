package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.UserRegistrationRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getById(Long id);

    UserResponseDTO getByEmail(String email);

    List<UserResponseDTO> getAll();

    UserResponseDTO save(UserRegistrationRequestDTO user);
}
