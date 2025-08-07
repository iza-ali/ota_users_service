package com.iaali.ota_users_service.service.impl;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.model.UserEntity;
import com.iaali.ota_users_service.repository.UserRepository;
import com.iaali.ota_users_service.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private UserMapper mapper;
    private EntityManager entityManager;

    @Override
    public UserResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));
    }

    @Override
    public UserResponseDTO getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(email, ErrorEnum.NOT_FOUND_USER_EMAIL));
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public UserResponseDTO save(UserRequestDTO user) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
        user.setPassword(encoder.encode(user.getPassword()));

        UserEntity entity = repository.save(mapper.toEntity(user));
        return mapper.toDTO(entity);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public UserResponseDTO updatePassword(Long id, String password) {
        Optional<UserEntity> exists = repository.findById(id);

        if (exists.isEmpty()) {
            throw new GlobalException(id, ErrorEnum.NOT_FOUND_ID);
        }

        UserEntity input = exists.get();

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
        input.setPasswordHash(encoder.encode(password));

        UserEntity response = repository.save(input);
        return mapper.toDTO(response);
    }

    @Override
    public UserResponseDTO updateEmail(Long id, String email) {
        Optional<UserEntity> exists = repository.findById(id);

        if (exists.isEmpty()) {
            throw new GlobalException(id, ErrorEnum.NOT_FOUND_ID);
        }

        UserEntity input = exists.get();
        input.setEmail(email);

        UserEntity response = repository.save(input);
        return mapper.toDTO(response);
    }

    @Override
    public void softDelete(Long id) {
        Optional<UserEntity> exists = repository.findById(id);

        if (exists.isEmpty()) {
            throw new GlobalException(id, ErrorEnum.NOT_FOUND_ID);
        }

        UserEntity input = exists.get();
        input.setDeleted(true);
        repository.save(input);
    }

    @Override
    public void hardDelete(Long id) {
        Optional<UserEntity> exists = repository.findById(id);

        if (exists.isEmpty()) {
            throw new GlobalException(id, ErrorEnum.NOT_FOUND_ID);
        }
        repository.deleteById(id);
    }
}
