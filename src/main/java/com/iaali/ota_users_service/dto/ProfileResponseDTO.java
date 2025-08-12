package com.iaali.ota_users_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iaali.ota_users_service.dto.validation.AvatarUpdate;
import com.iaali.ota_users_service.dto.validation.BioUpdate;
import com.iaali.ota_users_service.dto.validation.CreateProfile;
import com.iaali.ota_users_service.dto.validation.UsernameUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProfileResponseDTO {

    private Long id;

    private String username;

    private String bio;

    private String avatarUrl;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
