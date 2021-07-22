package com.market.skin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "NO_CONTENT")
public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(){
        super("Could not found any record matched.");
    }
}
