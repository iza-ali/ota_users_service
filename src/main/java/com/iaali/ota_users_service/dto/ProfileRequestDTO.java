package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.AvatarUpdate;
import com.iaali.ota_users_service.dto.validation.BioUpdate;
import com.iaali.ota_users_service.dto.validation.CreateProfile;
import com.iaali.ota_users_service.dto.validation.UsernameUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 255)
    private String bio;

    @NotBlank(message = "Photo is required", groups = {AvatarUpdate.class})
    private String avatarUrl;
}
