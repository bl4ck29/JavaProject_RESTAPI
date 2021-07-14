package com.market.skin.exception;

public class UserExistedById extends RuntimeException{
    public UserExistedById(int id){
        super(String.format("User with %d is already existed", id));
    }
}
