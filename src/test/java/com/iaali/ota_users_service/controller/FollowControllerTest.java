package com.iaali.ota_users_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaali.ota_users_service.dto.FollowRequestDTO;
import com.iaali.ota_users_service.dto.FollowResponseDTO;
import com.iaali.ota_users_service.service.FollowService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FollowService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getFollowById_Successful() throws Exception {
        Long id = 1L;

        FollowResponseDTO responseDTO = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        when(service.getById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/follows/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.followerId").value(1L))
                .andExpect(jsonPath("$.followedId").value(2L))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"));

        verify(service, times(1)).getById(id);
    }

    @Test
    void getFollowById_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/follows/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getById(id);
    }

    @Test
    void getAllFollows_Successful() throws Exception {
        FollowResponseDTO response1 = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 2L, 1L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowResponseDTO> list = new ArrayList<>();
        list.add(response1);
        list.add(response2);

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/follows"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].followerId").value(1L))
                .andExpect(jsonPath("$[0].followedId").value(2L))
                .andExpect(jsonPath("$[0].createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].followerId").value(2L))
                .andExpect(jsonPath("$[1].followedId").value(1L))
                .andExpect(jsonPath("$[1].createdAt").value("01-05-2025 12:00:00"));

        verify(service, times(1)).getAll();
    }

    @Test
    void getAllFollows_ListEmpty() throws Exception{

        List<FollowResponseDTO> list = new ArrayList<>();

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/follows"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAll();
    }

    @Test
    void getFollowingByProfileId_Successful() throws Exception {
        Long id = 1L;

        FollowResponseDTO response1 = new FollowResponseDTO(1L, 1L, 2L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 1L, 3L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowResponseDTO> list = new ArrayList<>();
        list.add(response1);
        list.add(response2);

        when(service.getListOfFollowing(id)).thenReturn(list);

        mockMvc.perform(get("/follows/{id}/following", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].followerId").value(1L))
                .andExpect(jsonPath("$[0].followedId").value(2L))
                .andExpect(jsonPath("$[0].createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].followerId").value(1L))
                .andExpect(jsonPath("$[1].followedId").value(3L))
                .andExpect(jsonPath("$[1].createdAt").value("01-05-2025 12:00:00"));

        verify(service, times(1)).getListOfFollowing(id);
    }

    @Test
    void getFollowingByProfileId_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/follows/{id}/following", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getListOfFollowing(id);
    }


    @Test
    void getFollowingByProfileId_ListEmpty() throws Exception{
        Long id = 1L;

        List<FollowResponseDTO> list = new ArrayList<>();

        when(service.getListOfFollowing(id)).thenReturn(list);

        mockMvc.perform(get("/follows/{id}/following", id))
                .andExpect(status().isOk());

        verify(service, times(1)).getListOfFollowing(id);
    }

    @Test
    void getFollowersByProfileId_Successful() throws Exception {
        Long id = 1L;

        FollowResponseDTO response1 = new FollowResponseDTO(1L, 2L, 1L,
                LocalDateTime.of(2025, 4, 1, 12, 0, 0));

        FollowResponseDTO response2 = new FollowResponseDTO(2L, 3L, 1L,
                LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        List<FollowResponseDTO> list = new ArrayList<>();
        list.add(response1);
        list.add(response2);

        when(service.getListOfFollowers(id)).thenReturn(list);

        mockMvc.perform(get("/follows/{id}/followers", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].followerId").value(2L))
                .andExpect(jsonPath("$[0].followedId").value(1L))
                .andExpect(jsonPath("$[0].createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].followerId").value(3L))
                .andExpect(jsonPath("$[1].followedId").value(1L))
                .andExpect(jsonPath("$[1].createdAt").value("01-05-2025 12:00:00"));

        verify(service, times(1)).getListOfFollowers(id);
    }

    @Test
    void getFollowersByProfileId_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/follows/{id}/followers", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getListOfFollowers(id);
    }

    @Test
    void getFollowersByProfileId_ListEmpty() throws Exception{
        Long id = 1L;

        List<FollowResponseDTO> list = new ArrayList<>();

        when(service.getListOfFollowing(id)).thenReturn(list);

        mockMvc.perform(get("/follows/{id}/followers", id))
                .andExpect(status().isOk());

        verify(service, times(1)).getListOfFollowers(id);
    }

    @Test
    void getFollowingCountByProfileId_ListEmpty() throws Exception{
        Long id = 1L;
        Long response = 3L;

        when(service.getFollowingCountById(id)).thenReturn(response);

        mockMvc.perform(get("/follows/{id}/following-count", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("3"));

        verify(service, times(1)).getFollowingCountById(id);
    }

    @Test
    void getFollowingCountByProfileId_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/follows/{id}/following-count", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getFollowingCountById(id);
    }

    @Test
    void getFollowersCountByProfileId_ListEmpty() throws Exception{
        Long id = 1L;
        Long response = 3L;

        when(service.getFollowerCountById(id)).thenReturn(response);

        mockMvc.perform(get("/follows/{id}/followers-count", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("3"));

        verify(service, times(1)).getFollowerCountById(id);
    }

    @Test
    void getFollowersCountByProfileId_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/follows/{id}/followers-count", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getFollowerCountById(id);
    }

    @Test
    void createNewFollow_Successful() throws Exception {
        FollowRequestDTO requestDTO = new FollowRequestDTO(1L, 2L);
        FollowResponseDTO responseDTO = new FollowResponseDTO(1L, 1L, 2L, LocalDateTime.of(2025, 5, 1, 12, 0, 0));

        when(service.save(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.followerId").value(1L))
                .andExpect(jsonPath("$.followedId").value(2L))
                .andExpect(jsonPath("$.createdAt").value("01-05-2025 12:00:00"));

        verify(service, times(1)).save(requestDTO);
    }

    @Test
    void createNewFollow_NullRequest() throws Exception {
        FollowRequestDTO requestDTO = new FollowRequestDTO(null, null);

        mockMvc.perform(post("/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).save(requestDTO);
    }

    @Test
    void hardDeleteFollow_Successful() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/follows/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(id);
    }

    @Test
    void hardDeleteFollow_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(delete("/follows/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).delete(id);
    }
}
