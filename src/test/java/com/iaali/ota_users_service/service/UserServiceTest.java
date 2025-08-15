package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.*;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.entity.Role;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.entity.UserEntity;
import com.iaali.ota_users_service.mapper.UserProfileCreationMapper;
import com.iaali.ota_users_service.repository.UserRepository;
import com.iaali.ota_users_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @Mock
    ProfileService profileService;

    @Mock
    UserProfileCreationMapper creationMapper;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void getById_Success() {
        Long id = 1L;

        UserEntity entity = new UserEntity(id, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO = new UserResponseDTO(id, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        UserResponseDTO response = service.getById(id);

        assertEquals(1L, response.getId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void getById_Unsuccessful() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getById(id));

        verify(repository, times(1)).findById(id);
    }

    @Test
    void getByEmail_Success() {
        String email = "test@email.com";

        UserEntity entity = new UserEntity(1L, email, "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO = new UserResponseDTO(1L, email,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        UserResponseDTO response = service.getByEmail(email);

        assertEquals(1L, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findByEmail(email);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void getByEmail_Unsuccessful() {
        String email = "test@email.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getByEmail(email));

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    void getAll_Successful() {
        UserEntity entity1 = new UserEntity(1L, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO1 = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);


        UserEntity entity2 = new UserEntity(2L, "test2@email.com", "password2", Role.User,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO2 = new UserResponseDTO(2L, "test2@email.com",
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null);

        List<UserEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTO(entity1)).thenReturn(responseDTO1);
        when(mapper.toDTO(entity2)).thenReturn(responseDTO2);

        List<UserResponseDTO> response = service.getAll();

        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals("test@email.com", response.getFirst().getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getFirst().getCreatedAt());
        assertNull(response.getFirst().getUpdatedAt());

        assertEquals(2L, response.get(1).getId());
        assertEquals("test2@email.com", response.get(1).getEmail());
        assertEquals(LocalDateTime.of(2025, 5, 1, 12, 0, 0), response.get(1).getCreatedAt());
        assertNull(response.get(1).getUpdatedAt());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(entity1);
        verify(mapper, times(1)).toDTO(entity2);
    }

    @Test
    void save_Successful() {
        UserProfileCombinedRequestDTO requestDTO = new UserProfileCombinedRequestDTO
                ("test@email.com", "password", "username", "bio", "url");

        UserEntity userEntity = new UserEntity(1L, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileEntity profileEntity = new ProfileEntity(1L, null, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserProfileCombinedResponseDTO responseDTO = new UserProfileCombinedResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, 1L, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.existsByEmail("test@email.com")).thenReturn(false);
        when(creationMapper.toUserEntity(requestDTO)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(creationMapper.toProfileEntity(requestDTO)).thenReturn(profileEntity);
        when(profileService.save(profileEntity)).thenReturn(profileEntity);
        when(creationMapper.toUserProfileResponse(userEntity, profileEntity)).thenReturn(responseDTO);

        UserProfileCombinedResponseDTO response = service.save(requestDTO);

        assertEquals(1L, response.getUserId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getUserCreatedAt());
        assertNull(response.getProfileUpdatedAt());
        assertEquals(1L, response.getProfileId());
        assertEquals("username", response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals("url", response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getProfileCreatedAt());
        assertNull(response.getProfileUpdatedAt());

        verify(repository, times(1)).existsByEmail("test@email.com");
        verify(creationMapper, times(1)).toUserEntity(requestDTO);
        verify(repository, times(1)).save(userEntity);
        verify(creationMapper, times(1)).toProfileEntity(requestDTO);
        verify(profileService, times(1)).save(profileEntity);
        verify(creationMapper, times(1)).toUserProfileResponse(userEntity, profileEntity);
    }

    @Test
    void save_EmailAlreadyExists() {
        UserProfileCombinedRequestDTO requestDTO = new UserProfileCombinedRequestDTO
                ("test@email.com", "password", "username", "bio", "url");

        when(repository.existsByEmail("test@email.com")).thenReturn(true);

        assertThrows(GlobalException.class, () -> service.save(requestDTO));

        verify(repository, times(1)).existsByEmail("test@email.com");
        verify(creationMapper, never()).toUserEntity(requestDTO);
        verify(creationMapper, never()).toProfileEntity(requestDTO);
    }


    @Test
    void updatePassword_Successful() {
        Long id = 1L;
        String password = "password";

        UserEntity entity = new UserEntity(id, "test@email.com", password, Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO = new UserResponseDTO(id, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        UserResponseDTO response = service.updatePassword(id, password);

        assertEquals(id, response.getId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void updatePassword_IdNotFound() {
        Long id = 1L;
        String password = "password";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.updatePassword(id, password));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void updateEmail_Successful() {
        Long id = 1L;
        String email = "test@email.com";

        UserEntity entity = new UserEntity(id, email, "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO = new UserResponseDTO(id, email,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        UserResponseDTO response = service.updateEmail(id, email);

        assertEquals(id, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void updateEmail_IdNotFound() {
        Long id = 1L;
        String email = "test@email.com";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.updateEmail(id, email));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void softDelete_Successful() {
        Long id = 1L;
        UserEntity entity = new UserEntity(id, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO profileResponse = new ProfileResponseDTO(id, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(profileService.getByUserId(id)).thenReturn(profileResponse);

        service.softDelete(id);

        assertTrue(entity.isDeleted());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(profileService, times(1)).getByUserId(id);
        verify(profileService, times(1)).softDelete(id);

    }

    @Test
    void softDelete_IdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.softDelete(id));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }
}
