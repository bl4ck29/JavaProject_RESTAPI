package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "INVALID_FORMAT")
public class InvalidFormatException extends RuntimeException{
    public InvalidFormatException(){
        super();
    }

    public InvalidFormatException(String message){
        super(message);
    }
}
