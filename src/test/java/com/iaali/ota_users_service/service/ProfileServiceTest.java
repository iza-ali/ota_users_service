package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.entity.Role;
import com.iaali.ota_users_service.entity.UserEntity;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.ProfileMapper;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.repository.ProfileRepository;
import com.iaali.ota_users_service.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository repository;

    @Mock
    ProfileMapper mapper;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    ProfileServiceImpl service;


    @Test
    void getById_Success() {
        Long id = 1L;

        ProfileEntity entity = new ProfileEntity(id, null, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(id, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.getById(id);

        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals("url", response.getAvatarUrl());
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
    void getByUsername_Success() {
        String username = "username";

        ProfileEntity entity = new ProfileEntity(1L, null, username, "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(1L, username, "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findByUsername(username)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.getByUsername(username);

        assertEquals(1L, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals("url", response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findByUsername(username);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void getByUsername_Unsuccessful() {
        String username = "username";

        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getByUsername(username));

        verify(repository, times(1)).findByUsername(username);
    }

    @Test
    void getByUserId_Successful() {
        Long id = 1L;

        UserEntity user = new UserEntity(1L, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileEntity entity = new ProfileEntity(id, user, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(id, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findByUserId(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.getByUserId(id);

        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals("url", response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findByUserId(id);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void getByUserId_Unsuccessful() {
        Long id = 1L;

        when(repository.findByUserId(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getByUserId(id));

        verify(repository, times(1)).findByUserId(id);
    }

    @Test
    void getAssociatedUser_Successful() {
        Long id = 1L;

        UserEntity user = new UserEntity(1L, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileEntity entity = new ProfileEntity(id, user, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO userResponse = new UserResponseDTO(id, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        UserResponseDTO response = service.getAssociatedUser(id);

        assertEquals(1L, response.getId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void getAssociatedUser_Unsuccessful() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getAssociatedUser(id));

        verify(repository, times(1)).findById(id);
    }

    @Test
    void getAll_Successful() {
        ProfileEntity entity1 = new ProfileEntity(1L, null, "username1", "bio1", "url1",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO response1 = new ProfileResponseDTO(1L, "username1", "bio1", "url1",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        ProfileEntity entity2 = new ProfileEntity(2L, null, "username2", "bio2", "url2",
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null, false);

        ProfileResponseDTO response2 = new ProfileResponseDTO(2L, "username2", "bio2", "url2",
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null);

        List<ProfileEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTO(entity1)).thenReturn(response1);
        when(mapper.toDTO(entity2)).thenReturn(response2);

        List<ProfileResponseDTO> response = service.getAll();

        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals("username1", response.getFirst().getUsername());
        assertEquals("bio1", response.getFirst().getBio());
        assertEquals("url1", response.getFirst().getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getFirst().getCreatedAt());
        assertNull(response.getFirst().getUpdatedAt());

        assertEquals(2L, response.get(1).getId());
        assertEquals("username2", response.get(1).getUsername());
        assertEquals("bio2", response.get(1).getBio());
        assertEquals("url2", response.get(1).getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 5, 1, 12, 0, 0), response.get(1).getCreatedAt());
        assertNull(response.get(1).getUpdatedAt());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(entity1);
        verify(mapper, times(1)).toDTO(entity2);
    }

    @Test
    void updateUsername_Successful() {
        Long id = 1L;
        String username = "username";

        ProfileEntity entity = new ProfileEntity(id, null, username, "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(id, username, "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.updateUsername(id, username);

        assertEquals(1L, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals("url", response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void updateUsername_IdNotFound() {
        Long id = 1L;
        String username = "username";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.updateUsername(id, username));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void updateBio_Successful() {
        Long id = 1L;
        String bio = "bio";

        ProfileEntity entity = new ProfileEntity(id, null, "username", bio, "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(id, "username", bio, "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.updateBio(id, bio);

        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals(bio, response.getBio());
        assertEquals("url", response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void updateBio_IdNotFound() {
        Long id = 1L;
        String bio = "bio";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.updateBio(id, bio));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAvatar_Successful() {
        Long id = 1L;
        String url = "url";

        ProfileEntity entity = new ProfileEntity(id, null, "username", "bio", url,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(id, "username", "bio", url,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        ProfileResponseDTO response = service.updateAvatar(id, url);

        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals("bio", response.getBio());
        assertEquals(url, response.getAvatarUrl());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void updateAvatar_IdNotFound() {
        Long id = 1L;
        String url = "url";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.updateAvatar(id, url));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void softDelete_Successful() {
        Long id = 1L;

        ProfileEntity entity = new ProfileEntity(id, null, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.softDelete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(entity);
    }

    @Test
    void softDelete_IdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.softDelete(id));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void hardDelete_Successful() {
        Long id = 1L;

        ProfileEntity entity = new ProfileEntity(id, null, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.hardDelete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void hardDelete_IdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.hardDelete(id));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }
}
