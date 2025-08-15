package com.iaali.ota_users_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaali.ota_users_service.dto.ProfileRequestDTO;
import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getProfileById_Successful() throws Exception {
        Long id = 1L;

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(1L, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/profiles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.bio").value("bio"))
                .andExpect(jsonPath("$.avatarUrl").value("url"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getById(id);
    }

    @Test
    void getProfileById_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/profiles/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getById(id);
    }

    @Test
    void getUserByUsername_Successful() throws Exception {
        String username = "username";

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(1L, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getByUsername(username)).thenReturn(responseDTO);

        mockMvc.perform(get("/profiles/username")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.bio").value("bio"))
                .andExpect(jsonPath("$.avatarUrl").value("url"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getByUsername(username);
    }

    @Test
    void getProfileByUsername_UsernameTooShort() throws Exception {
        String username = "te";

        mockMvc.perform(get("/profiles/username")
                        .param("username", username))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getByUsername(username);
    }

    @Test
    void getProfileByUsername_UsernameTooLong() throws Exception {
        String username = "testtesttesttesttesttest";

        mockMvc.perform(get("/profiles/username")
                        .param("username", username))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getByUsername(username);
    }

    @Test
    void getProfileByUserId_Successful() throws Exception {
        Long id = 1L;

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(2L, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getByUserId(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/profiles/{id}/user-id", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.bio").value("bio"))
                .andExpect(jsonPath("$.avatarUrl").value("url"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getByUserId(id);
    }

    @Test
    void getProfileByUserId_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/profiles/{id}/user-id", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getByUserId(id);
    }

    @Test
    void getAssociatedUser_Successful() throws Exception {
        Long id = 1L;

        UserResponseDTO responseDTO = new UserResponseDTO(2L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getAssociatedUser(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/profiles/{id}/user", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getAssociatedUser(id);
    }

    @Test
    void getAssociatedUser_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/profiles/{id}/user", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getAssociatedUser(id);
    }

    @Test
    void getAllProfiles_Successful() throws Exception {
        ProfileResponseDTO response1 = new ProfileResponseDTO(1L, "username1", "bio1", "url1",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        ProfileResponseDTO response2 = new ProfileResponseDTO(2L, "username2", "bio2", "url2",
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null);

        List<ProfileResponseDTO> list = new ArrayList<>();
        list.add(response1);
        list.add(response2);

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("username1"))
                .andExpect(jsonPath("$[0].bio").value("bio1"))
                .andExpect(jsonPath("$[0].avatarUrl").value("url1"))
                .andExpect(jsonPath("$[0].createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").doesNotExist())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value("username2"))
                .andExpect(jsonPath("$[1].bio").value("bio2"))
                .andExpect(jsonPath("$[1].avatarUrl").value("url2"))
                .andExpect(jsonPath("$[1].createdAt").value("01-05-2025 12:00:00"))
                .andExpect(jsonPath("$[1].updatedAt").doesNotExist());

        verify(service, times(1)).getAll();
    }

    @Test
    void getAllProfiles_ListEmpty() throws Exception{

        List<ProfileResponseDTO> list = new ArrayList<>();

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAll();
    }

    @Test
    void editProfileUsername_Successful() throws Exception {
        Long id = 1L;
        String username = "username";
        ProfileRequestDTO request = new ProfileRequestDTO(username, null, null);
        ProfileResponseDTO response = new ProfileResponseDTO(1L, username, "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.updateUsername(id, username)).thenReturn(response);

        mockMvc.perform(patch("/profiles/{id}/username", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.bio").value("bio"))
                .andExpect(jsonPath("$.avatarUrl").value("url"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).updateUsername(id, username);
    }

    @Test
    void editProfileUsername_UsernameTooShort() throws Exception {
        Long id = 1L;
        String username = "te";
        ProfileRequestDTO request = new ProfileRequestDTO(username, null, null);

        mockMvc.perform(patch("/profiles/{id}/username", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updateUsername(id, username);
    }

    @Test
    void editProfileUsername_UsernameTooLong() throws Exception {
        Long id = 1L;
        String username = "testtesttesttesttesttest";
        ProfileRequestDTO request = new ProfileRequestDTO(username, null, null);

        mockMvc.perform(patch("/profiles/{id}/username", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updateUsername(id, username);
    }

    @Test
    void editProfileBio_Successful() throws Exception {
        Long id = 1L;
        String bio = "bio";
        ProfileRequestDTO request = new ProfileRequestDTO(null, bio, null);
        ProfileResponseDTO response = new ProfileResponseDTO(1L, "username", bio, "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.updateBio(id, bio)).thenReturn(response);

        mockMvc.perform(patch("/profiles/{id}/bio", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.bio").value(bio))
                .andExpect(jsonPath("$.avatarUrl").value("url"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).updateBio(id, bio);
    }

    @Test
    void editProfileBio_BioTooLong() throws Exception {
        Long id = 1L;
        String bio = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        ProfileRequestDTO request = new ProfileRequestDTO(null, bio, null);

        mockMvc.perform(patch("/profiles/{id}/bio", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updateBio(id, bio);
    }

    @Test
    void editProfileAvatar_Successful() throws Exception {
        Long id = 1L;
        String avatarUrl = "url";
        ProfileRequestDTO request = new ProfileRequestDTO(null, null, avatarUrl);
        ProfileResponseDTO response = new ProfileResponseDTO(1L, "username", "bio", "url",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.updateAvatar(id, avatarUrl)).thenReturn(response);

        mockMvc.perform(patch("/profiles/{id}/avatar", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.bio").value("bio"))
                .andExpect(jsonPath("$.avatarUrl").value(avatarUrl))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).updateAvatar(id, avatarUrl);
    }
}
