package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.FollowRequestDTO;
import com.iaali.ota_users_service.dto.FollowResponseDTO;
import com.iaali.ota_users_service.service.FollowService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/follows")
@AllArgsConstructor
public class FollowController {

    private final FollowService service;

    // Internal error 500 is sent automatically when necessary for all requests

    @GetMapping("/{id}")
    public ResponseEntity<FollowResponseDTO> getFollowById(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FollowResponseDTO>> getAllFollows() {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<FollowResponseDTO>> getFollowingByProfileId(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getListOfFollowing(id));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<FollowResponseDTO>> getFollowersByProfileId(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getListOfFollowers(id));
    }

    @GetMapping("/{id}/following-count")
    public ResponseEntity<Long> getFollowingCountByProfileId(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getFollowingCountById(id));
    }

    @GetMapping("/{id}/followers-count")
    public ResponseEntity<Long> getFollowersCountByProfileId(@PathVariable @NotNull @Positive Long id) {

        // Bad request is sent through validation when the ID is out of bounds
        return ResponseEntity.ok(service.getFollowerCountById(id));
    }
    
    @PostMapping
    public ResponseEntity<FollowResponseDTO> createNewFollow(@Valid @RequestBody FollowRequestDTO follow) {

        //Bad Request sent through validation when IDs are out of bounds
        FollowResponseDTO response = service.save(follow);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollowById(@PathVariable @NotNull @Positive Long id) {

        //Bad Request sent through validation when IDs are out of bounds
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}