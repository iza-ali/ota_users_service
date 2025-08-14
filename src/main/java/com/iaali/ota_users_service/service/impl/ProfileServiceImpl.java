package com.iaali.ota_users_service.service.impl;

import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.ProfileMapper;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.repository.ProfileRepository;
import com.iaali.ota_users_service.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository repository;
    private ProfileMapper mapper;

    private UserMapper userMapper;

    @Override
    public ProfileResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));
    }

    @Override
    public ProfileResponseDTO getByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(username, ErrorEnum.NOT_FOUND_PROFILE_USERNAME));
    }

    @Override
    public ProfileResponseDTO getByUserId(Long id) {
        return repository.findByUserId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));
    }

    @Override
    public UserResponseDTO getAssociatedUser(Long id) {
        ProfileEntity profile = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        return userMapper.toDTO(profile.getUser());
    }

    @Override
    public List<ProfileResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ProfileEntity save(ProfileEntity profile) {
        return repository.save(profile);
    }

    @Override
    public ProfileResponseDTO updateUsername(Long id, String username) {
        ProfileEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        if (repository.existsByUsername(username)){
            throw new GlobalException(username, ErrorEnum.CONFLICT_PROFILE_USERNAME_ALREADY_EXISTS);
        }

        entity.setUsername(username);
        ProfileEntity response = repository.save(entity);
        return mapper.toDTO(response);
    }

    @Override
    public ProfileResponseDTO updateBio(Long id, String bio) {
        ProfileEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        entity.setBio(bio);
        ProfileEntity response = repository.save(entity);
        return mapper.toDTO(response);
    }

    @Override
    public ProfileResponseDTO updateAvatar(Long id, String avatarUrl) {
        ProfileEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        entity.setAvatarUrl(avatarUrl);
        ProfileEntity response = repository.save(entity);
        return mapper.toDTO(response);
    }

    @Override
    public void softDelete(Long id) {
        ProfileEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        entity.setDeleted(true);
        repository.save(entity);
    }

    @Override
    public void hardDelete(Long id) {
        repository.findById(id).orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        repository.deleteById(id);
        // Associated user is automatically deleted due to CascadeType.REMOVE
    }
}
