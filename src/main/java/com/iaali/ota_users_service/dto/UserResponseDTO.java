package com.iaali.ota_users_service.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    private Long id;

    @Email
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
