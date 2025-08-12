package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileRequestDTO {

    @NotBlank(message = "Username is required", groups = {CreateProfile.class, UsernameUpdate.class})
    private String username;

    @NotBlank(message = "Username is required", groups = {BioUpdate.class})
    private String bio;

    @NotBlank(message = "Photo is required", groups = {AvatarUpdate.class, CreateProfile.class})
    private String avatarUrl;
}
