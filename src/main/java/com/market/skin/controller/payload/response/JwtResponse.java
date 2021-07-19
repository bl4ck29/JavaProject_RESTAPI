package com.market.skin.controller.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String email;
    private List<String> roles;

    public JwtResponse (String accessToken, Long id, String username, String email, List<String> roles){
        this.token = accessToken;
        this.id = id;
        this.userName = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken(){return this.token;}
    public Long getId(){return this.id;}
    public String getUserName(){return this.userName;}
    public String getEmail(){return this.email;}
    public String getTokenType(){return this.type;}
    public List<String> getRoles(){return this.roles;}

    public void setAccessToken(String token){this.token = token;}
    public void setId(Long id){this.id = id;}
    public void setUserName(String name){this.userName = name;}
    public void setEmail(String email){this.email = email;}
    public void setTokenType(String tokenType){this.type = tokenType;}
    public void setRoles(List<String> roles){this.roles = roles;}
}
