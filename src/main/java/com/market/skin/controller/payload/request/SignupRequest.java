package com.market.skin.controller.payload.request;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotBlank @Size(min = 4, max = 15)
	private String userName;
    @NotBlank @Size(min=3, max=3)
	private String login_type;
    @Email @Size(max = 20)
	private String email;
    @NotBlank @Size(min=10, max=100)
    private String password;
    @NotBlank @Column(length = 200)
    private String profile;
    private Set<String> role;

    public String getUserName(){return this.userName;}
    public String getLoginType(){return this.login_type;}
    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    public String getProfile(){return this.profile;}
    public Set<String> getRoles(){return this.role;}

    public void setUserName(String name){this.userName = name;}
    public void setLoginType(String loginType){this.login_type = loginType;}
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setProfile(String profile){this.profile = profile;}
    public void setRole(Set<String> role){this.role = role;}
}
