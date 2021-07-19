package com.market.skin.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name", "email", "profile"})},
	indexes = @Index(columnList = "user_name", name = "name_ind"))
public class Users {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

    @NotBlank @Size(min = 4, max = 15) @Column(name = "user_name")
	private String userName;
	
    @NotBlank @Column(length = 3)
	private String login_type;
    
    @Email @Size(max = 20)
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();

	@NotBlank @Size(min = 10, max = 200)
    private String profile;

    @NotBlank @Size(min = 10, max = 100)
	@Column(name = "user_password")
	private String password;

	@OneToMany(targetEntity=Transactions.class, mappedBy="user")    
	protected List<Transactions> trans = new ArrayList<>();

	public Users(){}
	public Users(String user_name, String login_type, String email, String profile, String user_password){
		this.userName = user_name;
		this.login_type = login_type;
		this.email = email;
		this.profile = profile;
		this.password = user_password;
	}

	public void setId(Long id){this.id = id;}
	public void setUserName(String user_name){this.userName = user_name;}
	public void setLoginType(String login_type){this.login_type = login_type;}
	public void setEmail(String email){this.email = email;}
	public void setProfile(String profile){this.profile = profile;}
	public void setPassword(String user_password){this.password = user_password;}
	public void setRoles(Set<Roles> roles){this.roles = roles;}

	public Long getId(){return this.id;}
	public String getUserName(){return this.userName;}
	public String getLoginType(){return this.login_type;}
	public String getEmail(){return this.email;}
	public String getProfile(){return this.profile;}
	public String getPassword(){return this.password;}
	public List<Transactions> getTrans(){return this.trans;}
	public Set<Roles> getRoles(){return this.roles;}
	
	@Override
	public boolean equals(Object other){
		if((other==null) || (other.getClass() != this.getClass())){
			return false;
		}
		Users user = (Users) other;
		return Objects.equals(user.getUserName(), this.userName) && Objects.equals(user.getEmail(), this.email);
	}
}
