package com.iaali.ota_users_service.controller;

import com.iaali.ota_users_service.dto.UserRequestDTO;
import com.iaali.ota_users_service.dto.UserResponseDTO;
import com.iaali.ota_users_service.dto.validation.CreateUser;
import com.iaali.ota_users_service.dto.validation.EmailUpdate;
import com.iaali.ota_users_service.dto.validation.PasswordUpdate;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.service.UserService;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserResponseDTO> registerNewUser(@Validated(CreateUser.class) @RequestBody UserRequestDTO user){

        //Bad Request automatically thrown when e-mail or password are not valid
        if (user.getId() != null){
            throw new GlobalException(ErrorEnum.BAD_REQUEST_ID_PROVIDED_FOR_POST);
        }
        UserResponseDTO response = service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Internal error 500 is sent automatically when necessary
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponseDTO> editUserPassword(@Validated(PasswordUpdate.class) @RequestBody UserRequestDTO user,
                                                              @NonNull @PathVariable Long id) {

        if (id < 1) {
            throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
            // Bad request is sent automatically when the ID is above the MAX_LONG or a string
        }
        UserResponseDTO updatedUser = service.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponseDTO> editUserEmail(@Validated(EmailUpdate.class) @RequestBody UserRequestDTO user,
                                                            @NonNull @PathVariable Long id) {

        if (id < 1) {
            throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
            // Bad request is sent automatically when the ID is above the MAX_LONG or a string
        }
        UserResponseDTO updatedUser = service.updateEmail(id, user.getEmail());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void softDeleteUser(@NonNull @PathVariable Long id) {
        if (id < 1) {
             throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
        }
        service.softDelete(id);
    }

    @DeleteMapping("/{id}/hard")
    public void hardDeleteUser(@NonNull @PathVariable Long id) {
        if (id < 1) {
            throw new GlobalException(id, ErrorEnum.BAD_REQUEST_ID_TOO_SMALL);
        }
        service.hardDelete(id);
    }
}
