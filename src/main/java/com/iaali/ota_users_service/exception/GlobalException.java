package com.iaali.ota_users_service.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final ErrorEnum error;

    public GlobalException(ErrorEnum error){
        super(error.getDescription());
        this.error = error;
    }

    public GlobalException(Long id, ErrorEnum error){
        super(error.getDescription() + id);
        this.error = error;
    }

    public GlobalException(String email, ErrorEnum error){
        super(error.getDescription() + email);
        this.error = error;
    }
}
