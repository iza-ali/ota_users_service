package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.CreateUserProfile;
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

    @NotBlank(message = "Email is required", groups = CreateUserProfile.class)
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long", groups = CreateUserProfile.class)
    @NotBlank(message = "Password is required", groups = CreateUserProfile.class)
    private String password;

    // Profile fields

    @NotBlank(message = "Username is required", groups = CreateUserProfile.class)
    @Size(min = 3, max = 20, groups = CreateUserProfile.class)
    private String username;

    @Size(max = 255, groups = CreateUserProfile.class)
    private String bio;

    private String avatarUrl;
}
