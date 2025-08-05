package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getById(Long id);

    UserDTO getByEmail(String email);

    List<UserDTO> getAll();
}
