package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.entity.Role;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.UserMapper;
import com.iaali.ota_users_service.entity.UserEntity;
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
        UserRequestDTO requestDTO = new UserRequestDTO("test@email.com", "password");

        UserEntity entity = new UserEntity(1L, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        UserResponseDTO response = service.save(requestDTO);

        assertEquals(1L, response.getId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0, 0), response.getCreatedAt());
        assertNull(response.getUpdatedAt());

        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(requestDTO);
        verify(mapper, times(1)).toDTO(entity);
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

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.softDelete(id);

        assertTrue(entity.isDeleted());
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
    void hardDelete_Successful(){
        Long id = 1L;
        UserEntity entity = new UserEntity(id, "test@email.com", "password", Role.User,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null, false);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        service.hardDelete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void hardDelete_IdNotFound(){
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> service.hardDelete(id));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(id);
    }
}
