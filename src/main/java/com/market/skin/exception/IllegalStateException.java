package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "ILLEGAL_STATE")
public class IllegalStateException extends RuntimeException{
    public IllegalStateException(){
        super();
    }

    public IllegalStateException(String message){
        super(message);
    }
}
