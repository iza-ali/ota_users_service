package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.FollowRequestDTO;
import com.iaali.ota_users_service.dto.FollowResponseDTO;
import com.iaali.ota_users_service.entity.FollowEntity;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.FollowMapper;
import com.iaali.ota_users_service.repository.FollowRepository;
import com.iaali.ota_users_service.service.impl.FollowServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    FollowRepository repository;

    @Mock
    FollowMapper mapper;

    @Mock
    ProfileService profileService;

    @InjectMocks
    FollowServiceImpl service;

    @Test
    void getById_Success() {
        Long id = 1L;

        ProfileEntity follower = new ProfileEntity();
        ProfileEntity followed = new ProfileEntity();

        FollowEntity entity = new FollowEntity(id, follower, followed,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO responseDTO = new FollowResponseDTO(id, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        FollowResponseDTO response = service.getById(id);

        assertEquals(1L, response.getId());
        assertEquals(1L, response.getFollowerId());
        assertEquals(2L, response.getFollowedId());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void getById_IdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.getById(id));

        verify(repository, times(1)).findById(id);
    }

    @Test
    void getAll_Successful() {
        ProfileEntity profile1 = new ProfileEntity();
        ProfileEntity profile2 = new ProfileEntity();

        FollowEntity entity1 = new FollowEntity(1L, profile1, profile2,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response1 = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowEntity entity2 = new FollowEntity(2L, profile2, profile1,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 2L, 1L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTO(entity1)).thenReturn(response1);
        when(mapper.toDTO(entity2)).thenReturn(response2);

        List<FollowResponseDTO> response = service.getAll();

        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals(1L, response.getFirst().getFollowerId());
        assertEquals(2L, response.getFirst().getFollowedId());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getFirst().getCreatedAt());

        assertEquals(2L, response.get(1).getId());
        assertEquals(2L, response.get(1).getFollowerId());
        assertEquals(1L, response.get(1).getFollowedId());
        assertEquals(LocalDateTime.of(2025, 5, 1, 12, 0, 0), response.get(1).getCreatedAt());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(entity1);
        verify(mapper, times(1)).toDTO(entity2);
    }

    @Test
    void getListOfFollowing_Successful() {
        ProfileEntity profile1 = new ProfileEntity();
        ProfileEntity profile2 = new ProfileEntity();
        ProfileEntity profile3 = new ProfileEntity();

        FollowEntity entity1 = new FollowEntity(1L, profile1, profile2,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response1 = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowEntity entity2 = new FollowEntity(2L, profile1, profile3,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 1L, 3L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTO(entity1)).thenReturn(response1);
        when(mapper.toDTO(entity2)).thenReturn(response2);

        List<FollowResponseDTO> response = service.getAll();

        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals(1L, response.getFirst().getFollowerId());
        assertEquals(2L, response.getFirst().getFollowedId());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getFirst().getCreatedAt());

        assertEquals(2L, response.get(1).getId());
        assertEquals(1L, response.get(1).getFollowerId());
        assertEquals(3L, response.get(1).getFollowedId());
        assertEquals(LocalDateTime.of(2025, 5, 1, 12, 0, 0), response.get(1).getCreatedAt());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(entity1);
        verify(mapper, times(1)).toDTO(entity2);
    }

    @Test
    void getListOfFollowers_Successful() {
        ProfileEntity profile1 = new ProfileEntity();
        ProfileEntity profile2 = new ProfileEntity();
        ProfileEntity profile3 = new ProfileEntity();

        FollowEntity entity1 = new FollowEntity(1L, profile2, profile1,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response1 = new FollowResponseDTO(1L, 2L, 1L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowEntity entity2 = new FollowEntity(2L, profile3, profile1,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 3L, 1L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTO(entity1)).thenReturn(response1);
        when(mapper.toDTO(entity2)).thenReturn(response2);

        List<FollowResponseDTO> response = service.getAll();

        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals(2L, response.getFirst().getFollowerId());
        assertEquals(1L, response.getFirst().getFollowedId());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getFirst().getCreatedAt());

        assertEquals(2L, response.get(1).getId());
        assertEquals(3L, response.get(1).getFollowerId());
        assertEquals(1L, response.get(1).getFollowedId());
        assertEquals(LocalDateTime.of(2025, 5, 1, 12, 0, 0), response.get(1).getCreatedAt());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(entity1);
        verify(mapper, times(1)).toDTO(entity2);
    }

    @Test
    void save_Successful() {
        ProfileEntity follower = new ProfileEntity();
        ProfileEntity followed = new ProfileEntity();

        FollowRequestDTO requestDTO = new FollowRequestDTO(1L, 2L);

        FollowEntity entity = new FollowEntity(1L, follower, followed,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO responseDTO = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        when(repository.existsByFollowerIdAndFollowedId(1L, 2L)).thenReturn(false);
        when(profileService.getEntityById(1L)).thenReturn(follower);
        when(profileService.getEntityById(2L)).thenReturn(followed);
        when(mapper.toEntity(follower, followed)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        FollowResponseDTO response = service.save(requestDTO);

        assertEquals(1L, response.getId());
        assertEquals(1L, response.getFollowerId());
        assertEquals(2L, response.getFollowedId());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());

        verify(repository, times(1)).existsByFollowerIdAndFollowedId(1L, 2L);
        verify(profileService, times(1)).getEntityById(1L);
        verify(profileService, times(1)).getEntityById(2L);
        verify(mapper, times(1)).toEntity(follower, followed);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDTO(entity);
    }

    @Test
    void save_FollowerEqualsFollowed() {
        FollowRequestDTO requestDTO = new FollowRequestDTO(1L, 1L);

        assertThrows(GlobalException.class, () -> service.save(requestDTO));

        verify(repository, never()).existsByFollowerIdAndFollowedId(1L, 1L);
        verify(profileService, never()).getEntityById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void save_FollowAlreadyExists() {
        FollowRequestDTO requestDTO = new FollowRequestDTO(1L, 2L);

        when(repository.existsByFollowerIdAndFollowedId(1L, 2L)).thenReturn(true);

        assertThrows(GlobalException.class, () -> service.save(requestDTO));

        verify(repository, times(1)).existsByFollowerIdAndFollowedId(1L, 2L);
        verify(profileService, never()).getEntityById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void delete_Successful() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void delete_IdNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(GlobalException.class, () -> service.delete(id));

        verify(repository, times(1)).existsById(id);
        verify(repository, never()).save(any());
    }
}
