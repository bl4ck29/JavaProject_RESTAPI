package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "SYS_TRANS")
public class HttpMessageNotReadableException extends RuntimeException{
    public HttpMessageNotReadableException(){
        super();
    }

    public HttpMessageNotReadableException(String message){
        super(message);
    }
}
