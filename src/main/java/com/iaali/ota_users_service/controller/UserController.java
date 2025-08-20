package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.UserProfileCombinedResponseDTO;
import com.iaali.ota_users_service.dto.UserProfileCombinedRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.validation.CreateUserProfile;
import com.iaali.ota_users_service.dto.validation.EmailUpdate;
import com.iaali.ota_users_service.dto.validation.PasswordUpdate;
import com.iaali.ota_users_service.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds

        return ResponseEntity.ok(service.getById(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam @NotBlank @Email String email) {

        // Bad request is sent through validation when e-mail is not formatted correctly

        return ResponseEntity.ok(service.getByEmail(email));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(service.getAll());
        // Internal error 500 response is shown automatically
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfileCombinedResponseDTO> registerNewUserAndProfile(@Validated(CreateUserProfile.class) @RequestBody UserProfileCombinedRequestDTO info) {

        //Bad Request sent through validation when e-mail or password are not valid

        UserProfileCombinedResponseDTO response = service.save(info);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Internal error 500 is sent automatically when necessary
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponseDTO> editUserPassword(@Validated(PasswordUpdate.class) @RequestBody UserRequestDTO user,
                                                              @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds or password is not valid

        UserResponseDTO updatedUser = service.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updatedUser);
        // Internal error 500 is sent automatically when necessary
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponseDTO> editUserEmail(@Validated(EmailUpdate.class) @RequestBody UserRequestDTO user,
                                                            @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds or e-mail is not valid

        UserResponseDTO updatedUser = service.updateEmail(id, user.getEmail());
        return ResponseEntity.ok(updatedUser);
        // Internal error 500 is sent automatically when necessary
    }

    // Delete in User also deletes in Profile

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds

        service.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // Internal error 500 is sent automatically when necessary
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteUser(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds

        service.hardDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // Internal error 500 is sent automatically when necessary
    }
}
