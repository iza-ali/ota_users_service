package com.iaali.ota_users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    @Email
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
