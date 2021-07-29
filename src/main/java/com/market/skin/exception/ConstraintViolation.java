package com.market.skin.exception;

public class ConstraintViolation extends RuntimeException{
    public ConstraintViolation(){
        super();
    }    
    public ConstraintViolation(String message){
        super(message);
    }
}
