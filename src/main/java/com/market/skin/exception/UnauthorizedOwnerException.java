package com.market.skin.exception;

public class UnauthorizedOwnerException extends RuntimeException{
    public UnauthorizedOwnerException(){
        super();
    }
    public UnauthorizedOwnerException(String message){
        super(message);
    }
}
