package com.market.skin.controller.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public String getUserName(){return this.username;}
    public String getPassword(){return this.password;}

    public void setUserName(String name){this.username = name;}
    public void setPassword(String passwd){this.password = passwd;}
}
