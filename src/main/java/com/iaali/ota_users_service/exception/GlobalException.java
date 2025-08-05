package com.iaali.ota_users_service.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final Long id;
    private final ErrorEnum error;

    public GlobalException(ErrorEnum error){
        super(error.getDescription());
        this.id = null;
        this.error = error;
    }

    public GlobalException(Long id, ErrorEnum error){
        super(error.getDescription() + id);
        this.id = id;
        this.error = error;
    }

    public GlobalException(String email, ErrorEnum error){
        super(error.getDescription() + email);
        this.id = null;
        this.error = error;
    }
}
