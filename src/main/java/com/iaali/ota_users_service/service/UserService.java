package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getById(Long id);

    UserResponseDTO getByEmail(String email);

    List<UserResponseDTO> getAll();

    UserResponseDTO save(UserRequestDTO user);

    boolean existsById(Long id);

    UserResponseDTO updatePassword(Long id, String password);

    UserResponseDTO updateEmail(Long id, String email);

    void softDelete(Long id);

    void hardDelete(Long id);
}
