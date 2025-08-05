package com.iaali.ota_users_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

//  For all entities
    NOT_FOUND_ID(HttpStatus.NOT_FOUND, "Could not find resource with ID "),
    BAD_REQUEST_ID_TOO_SMALL(HttpStatus.BAD_REQUEST, "ID must be over 0. Given ID: "),
    BAD_REQUEST_ID_PROVIDED_FOR_POST(HttpStatus.BAD_REQUEST, "ID must not be provided when saving a new resource"),
    BAD_REQUEST_ID_INPUT_NOT_VALID_FOR_UPDATE(HttpStatus.BAD_REQUEST, "ID in PUT request body must be null or the same as the ID in the URI. URI ID is "),

//  For User entity
    NOT_FOUND_USER_EMAIL(HttpStatus.NOT_FOUND, "Could not find User with e-mail ");


    private final HttpStatus status;
    private final String description;

}
