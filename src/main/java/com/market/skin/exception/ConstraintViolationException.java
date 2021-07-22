package com.market.skin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Constraint violation")
public class ConstraintViolationException extends RuntimeException{
    public ConstraintViolationException(){
    }

    public ConstraintViolationException(String message){
        super(message);
    }
}