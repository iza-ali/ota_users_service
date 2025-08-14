package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.ProfileRequestDTO;
import com.iaali.ota_users_service.dto.ProfileResponseDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.dto.validation.*;
import com.iaali.ota_users_service.service.ProfileService;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(@NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds

        return ResponseEntity.ok(service.getById(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/username")
    public ResponseEntity<ProfileResponseDTO> getProfileByUsername(@RequestParam @Size(min = 3, max = 20) String username) {

        // Bad request is sent through validation when username length is out of bounds

        return ResponseEntity.ok(service.getByUsername(username));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/{id}/user-id")
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when ID is out of bounds

        return ResponseEntity.ok(service.getByUserId(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserResponseDTO> getAssociatedUser(@NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds

        return ResponseEntity.ok(service.getAssociatedUser(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {

        return ResponseEntity.ok(service.getAll());
        // Internal error 500 response is shown automatically
    }

    // Can only create profile through UserController

    @PatchMapping("/{id}/username")
    public ResponseEntity<ProfileResponseDTO> editProfileUsername(@Validated(UsernameUpdate.class) @RequestBody ProfileRequestDTO profile,
                                                            @NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when username is out of bounds

        ProfileResponseDTO updatedProfile = service.updateUsername(id, profile.getUsername());
        return ResponseEntity.ok(updatedProfile);
        // Internal error 500 is sent automatically when necessary
    }

    @PatchMapping("/{id}/bio")
    public ResponseEntity<ProfileResponseDTO> editProfileBio(@Validated(BioUpdate.class) @RequestBody ProfileRequestDTO profile,
                                                                  @NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when bio is out of bounds

        ProfileResponseDTO updatedProfile = service.updateBio(id, profile.getBio());
        return ResponseEntity.ok(updatedProfile);
        // Internal error 500 is sent automatically when necessary
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<ProfileResponseDTO> editProfileAvatar(@Validated(AvatarUpdate.class) @RequestBody ProfileRequestDTO profile,
                                                             @NonNull @PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when given url is blank

        ProfileResponseDTO updatedProfile = service.updateAvatar(id, profile.getAvatarUrl());
        return ResponseEntity.ok(updatedProfile);
        // Internal error 500 is sent automatically when necessary
    }

    // Can only delete profile through UserController
}