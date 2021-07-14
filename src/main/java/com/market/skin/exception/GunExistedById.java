package com.market.skin.exception;

public class GunExistedById extends RuntimeException{
    public GunExistedById(int id){
        super("Gun with id {id} is already existed");
    }
    
}
