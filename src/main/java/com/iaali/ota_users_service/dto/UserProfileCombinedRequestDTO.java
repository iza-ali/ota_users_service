package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.CreateUserProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileCombinedRequestDTO {

    //User fields

    @Email(groups = CreateUserProfile.class)
    @NotBlank(message = "Email is required", groups = CreateUserProfile.class)
    private String email;

    @Size(min = 8, max = 255, message = "Password must be longer than 8 characters and shorter than 255 characters", groups = CreateUserProfile.class)
    @NotBlank(message = "Password is required", groups = CreateUserProfile.class)
    private String password;

    // Profile fields

    @NotBlank(message = "Username is required", groups = CreateUserProfile.class)
    @Size(min = 3, max = 20, groups = CreateUserProfile.class)
    private String username;

    @Size(max = 255, groups = CreateUserProfile.class)
    private String bio;

    @Size(max = 2048, message = "Error, image URL too long", groups = {CreateUserProfile.class})
    private String avatarUrl;
}
