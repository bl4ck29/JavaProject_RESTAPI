package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "SYS_TRANS")
public class ApplicationContextException extends RuntimeException{
    public ApplicationContextException(){
        super();
    }

    public ApplicationContextException(String message){
        super(message);
    }
}
