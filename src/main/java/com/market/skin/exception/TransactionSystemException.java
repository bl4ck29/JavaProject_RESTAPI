package com.market.skin.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "SYS_TRANS")
public class TransactionSystemException extends RuntimeException{
    public TransactionSystemException(){
        super();
    }

    public TransactionSystemException(String message){
        super(message);
    }
}
