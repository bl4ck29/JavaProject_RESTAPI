package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "PARAMETER_MISSING")
public class MissingServletRequestParameterException extends RuntimeException{
    public MissingServletRequestParameterException(){
        super();
    }

    public MissingServletRequestParameterException(String message){
        super(message);
    }
}
