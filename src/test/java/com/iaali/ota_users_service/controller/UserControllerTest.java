package com.iaali.ota_users_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.service.UserService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getUserById_Successful() throws Exception {
        Long id = 1L;

        UserResponseDTO responseDTO = new UserResponseDTO(id, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getById(id);
    }

    @Test
    void getUserById_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getById(id);
    }

    @Test
    void getUserByEmail_Successful() throws Exception {
        String email = "test@email.com";
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.getByEmail(email)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).getByEmail(email);
    }

    @Test
    void getUserByEmail_EmailFormattedWrong() throws Exception {
        String email = "test";

        mockMvc.perform(get("/users/email")
                        .param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).getByEmail(email);
    }

    @Test
    void getAllUsers_Successful() throws Exception {
        UserResponseDTO responseDTO1 = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        UserResponseDTO responseDTO2 = new UserResponseDTO(2L, "test2@email.com",
                LocalDateTime.of(2025, 5, 1, 12, 0, 0), null);

        List<UserResponseDTO> list = new ArrayList<>();
        list.add(responseDTO1);
        list.add(responseDTO2);

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].email").value("test@email.com"))
                .andExpect(jsonPath("$[0].createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").doesNotExist())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].email").value("test2@email.com"))
                .andExpect(jsonPath("$[1].createdAt").value("01-05-2025 12:00:00"))
                .andExpect(jsonPath("$[1].updatedAt").doesNotExist());

        verify(service, times(1)).getAll();
    }

    @Test
    void getAllUsers_ListEmpty() throws Exception{

        List<UserResponseDTO> list = new ArrayList<>();

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAll();
    }

    @Test
    void registerNewUser_Successful() throws Exception {
        UserRequestDTO request = new UserRequestDTO("test@email.com", "password");
        UserResponseDTO response = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.save(request)).thenReturn(response);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).save(request);
    }

    @Test
    void registerNewUser_EmailFormattedWrong() throws Exception {
        UserRequestDTO request = new UserRequestDTO("test", "password");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).save(request);
    }

    @Test
    void registerNewUser_PasswordTooShort() throws Exception {
        UserRequestDTO request = new UserRequestDTO("test@email.com", "test");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).save(request);
    }

    @Test
    void editUserPassword_Successful() throws Exception {
        Long id = 1L;
        String password = "password";
        UserRequestDTO request = new UserRequestDTO(null, password);
        UserResponseDTO response = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.updatePassword(id, password)).thenReturn(response);

        mockMvc.perform(patch("/users/{id}/password", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).updatePassword(id, password);
    }

    @Test
    void editUserPassword_PasswordTooShort() throws Exception {
        Long id = 1L;
        String password = "test";
        UserRequestDTO request = new UserRequestDTO(null, password);

        mockMvc.perform(patch("/users/{id}/password", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updatePassword(id, password);
    }

    @Test
    void editUserPassword_IdNegative() throws Exception {
        Long id = -1L;
        String password = "test";
        UserRequestDTO request = new UserRequestDTO(null, password);

        mockMvc.perform(patch("/users/{id}/password", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updatePassword(id, password);
    }

    @Test
    void editUserEmail_Successful() throws Exception {
        Long id = 1L;
        String email = "test@email.com";
        UserRequestDTO request = new UserRequestDTO(email, null);
        UserResponseDTO response = new UserResponseDTO(1L, "test@email.com",
                LocalDateTime.of(2025, 4, 1, 12, 0, 0), null);

        when(service.updateEmail(id, email)).thenReturn(response);

        mockMvc.perform(patch("/users/{id}/email", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.createdAt").value("01-04-2025 12:00:00"))
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        verify(service, times(1)).updateEmail(id, email);
    }

    @Test
    void editUserEmail_EmailFormattedWrong() throws Exception {
        Long id = 1L;
        String email = "test";
        UserRequestDTO request = new UserRequestDTO(email, null);

        mockMvc.perform(patch("/users/{id}/email", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updateEmail(id, email);
    }

    @Test
    void editUserEmail_IdNegative() throws Exception {
        Long id = -1L;
        String email = "test";
        UserRequestDTO request = new UserRequestDTO(email, null);

        mockMvc.perform(patch("/users/{id}/email", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verify(service, never()).updateEmail(id, email);
    }

    @Test
    void softDeleteUser_Successful() throws Exception {
        Long id = 1L;

        doNothing().when(service).softDelete(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).softDelete(id);
    }

    @Test
    void softDeleteUser_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).softDelete(id);
    }

    @Test
    void hardDeleteUser_Successful() throws Exception {
        Long id = 1L;

        doNothing().when(service).hardDelete(id);

        mockMvc.perform(delete("/users/{id}/hard", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).hardDelete(id);
    }

    @Test
    void hardDeleteUser_IdNegative() throws Exception {
        Long id = -1L;

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()));

        verify(service, never()).hardDelete(id);
    }
}
