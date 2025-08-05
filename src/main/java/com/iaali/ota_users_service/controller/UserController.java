package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.UserRegistrationRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@NonNull @PathVariable Long id){

        if (id < 1) {
            throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
        }

        return ResponseEntity.ok(service.getById(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam @Email String email){

        return ResponseEntity.ok(service.getByEmail(email));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
        // Internal error 500 response is shown automatically
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerNewUser(@Valid @RequestBody UserRegistrationRequestDTO user){

        UserResponseDTO response = service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Internal error 500 is sent automatically when necessary
    }
}
