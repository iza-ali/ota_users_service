package com.iaali.ota_users_service.dto;

import com.iaali.ota_users_service.dto.validation.CreateUser;
import com.iaali.ota_users_service.dto.validation.EmailUpdate;
import com.iaali.ota_users_service.dto.validation.PasswordUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserRequestDTO {

    @Email(groups = {EmailUpdate.class, CreateUser.class})
    @NotBlank(message = "Email is required", groups = {EmailUpdate.class, CreateUser.class})
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long", groups = {PasswordUpdate.class, CreateUser.class})
    @NotBlank(message = "Password is required", groups = {PasswordUpdate.class, CreateUser.class})
    private String password;

}