package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "SYS_TRANS")
public class PSQLException extends RuntimeException{
    public PSQLException(){
        super();
    }

    public PSQLException(String message){
        super(message);
    }
}
