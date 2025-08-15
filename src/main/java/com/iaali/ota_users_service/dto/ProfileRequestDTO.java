package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileRequestDTO {

    @NotBlank(message = "Username is required", groups = {UsernameUpdate.class})
    @Size(min = 3, max = 20, groups = {UsernameUpdate.class})
    private String username;

    @NotBlank(message = "Bio is required", groups = {BioUpdate.class})
    @Size(max = 255, groups = {BioUpdate.class})
    private String bio;

    @NotBlank(message = "Photo is required", groups = {AvatarUpdate.class})
    private String avatarUrl;
}
