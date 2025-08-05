package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.UserDTO;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    public ResponseEntity<UserDTO> getUserById(@NonNull @PathVariable Long id){

        if (id < 1) {
            throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
        }

        return ResponseEntity.ok(service.getById(id));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam @Email String email){

        return ResponseEntity.ok(service.getByEmail(email));
        // Internal error 500 is sent automatically when necessary
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
        // Internal error 500 response is shown automatically
    }
}
