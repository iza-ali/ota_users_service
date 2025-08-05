package com.iaali.ota_users_service.service.impl;

import com.iaali.ota_users_service.dto.UserDTO;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.repository.UserRepository;
import com.iaali.ota_users_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private UserMapper mapper;

    @Override
    public UserDTO getById(Long id) {

        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));
    }

    @Override
    public UserDTO getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(email, ErrorEnum.NOT_FOUND_USER_EMAIL));
    }

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }


}
