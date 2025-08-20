package com.iaali.ota_users_service.service.impl;

import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedRequestDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.entity.UserEntity;
import com.iaali.ota_users_service.mapper.UserProfileCreationMapper;
import com.iaali.ota_users_service.repository.UserRepository;
import com.iaali.ota_users_service.service.ProfileService;
import com.iaali.ota_users_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private UserMapper mapper;

    ProfileService profileService;
    UserProfileCreationMapper creationMapper;

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
    public Long getAssociatedProfileId(Long id) {
        ProfileResponseDTO profileDTO = profileService.getByUserId(id);
        return profileDTO.getId();
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public UserProfileCombinedResponseDTO save(UserProfileCombinedRequestDTO info) {
        String email = info.getEmail();

        if (repository.existsByEmail(email)){
            throw new GlobalException(email, ErrorEnum.CONFLICT_USER_EMAIL_ALREADY_EXISTS);
        }

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
        info.setPassword(encoder.encode(info.getPassword()));

        UserEntity user = repository.save(creationMapper.toUserEntity(info));
        ProfileEntity profileEntity = creationMapper.toProfileEntity(info);
        profileEntity.setUser(user);
        ProfileEntity profile = profileService.save(profileEntity);
        return creationMapper.toUserProfileResponse(user, profile);
    }

    @Override
    public UserResponseDTO updatePassword(Long id, String password) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
        entity.setPasswordHash(encoder.encode(password));

        UserEntity response = repository.save(entity);
        return mapper.toDTO(response);
    }

    @Override
    public UserResponseDTO updateEmail(Long id, String email) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        if (repository.existsByEmail(email)){
            throw new GlobalException(email, ErrorEnum.CONFLICT_USER_EMAIL_ALREADY_EXISTS);
        }

        entity.setEmail(email);

        UserEntity response = repository.save(entity);
        return mapper.toDTO(response);
    }

    @Override
    public void softDelete(Long id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));

        entity.setDeleted(true);
        repository.save(entity);

        Long profileId = getAssociatedProfileId(id);
        profileService.softDelete(profileId);
    }

    @Override
    public void hardDelete(Long id) {
        Long profileId = getAssociatedProfileId(id);
        profileService.hardDelete(profileId);
    }
}
