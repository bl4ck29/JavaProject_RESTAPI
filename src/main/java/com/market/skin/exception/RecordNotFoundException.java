package com.market.skin.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(){
        super();
    }

    public RecordNotFoundException(String message){
        super(message);
    }
}
