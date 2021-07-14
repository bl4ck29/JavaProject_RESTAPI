package com.market.skin.exception;

public class UserIdNotFound extends RuntimeException{
    public UserIdNotFound(int id){
        super(String.format("User with id %d could not be found", id));
    }
}
