package com.market.skin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.relational.core.sql.FalseCondition;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name", "email", "profile"})})
public class Users {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

    @NotBlank @Size(min = 4, max = 15)
	private String user_name;
	
    @NotBlank @Column(length = 3)
	private String login_type;
    
    @NaturalId @Email @Size(max = 20)
	private String email;

	private int role_id;
	@ManyToOne()
	@JoinColumn(name="role_id", referencedColumnName = "role_id", insertable = false, updatable = false)    
	private Roles role;

	@NotBlank @Size(min = 10, max = 200)
    private String profile;

    @NotBlank @Size(min = 10, max = 20)
	private String user_password;

	@OneToMany(targetEntity=Transactions.class, mappedBy="user")    
	private List<Transactions> trans = new ArrayList<>();

	public Users(){}
	public Users(int id, String user_name, String login_type, String email, int role_id, String profile, String user_password){
		this.user_id = id;
		this.user_name = user_name;
		this.login_type = login_type;
		this.email = email;
		this.role_id = role_id;
		this.profile = profile;
		this.user_password = user_password;
	}

	public void setId(int id){this.user_id = id;}
	public void setRoleId(int role_id){this.role_id = role_id;}
	public void setUserName(String user_name){this.user_name = user_name;}
	public void setLoginType(String login_type){this.login_type = login_type;}
	public void setEmail(String email){this.email = email;}
	public void setProfile(String profile){this.profile = profile;}
	public void setPassword(String user_password){this.user_password = user_password;}

	public int getId(){return this.user_id;}
	public int getRoleId(){return this.role_id;}
	public String getUserName(){return this.user_name;}
	public String getLoginType(){return this.login_type;}
	public String getEmail(){return this.email;}
	public String getProfile(){return this.profile;}
	public String getPassword(){return this.user_password;}
	
	@Override
	public boolean equals(Object other){
		if((other==null) || (other.getClass() != this.getClass())){
			return false;
		}
		Users user = (Users) other;
		return Objects.equals(user.getUserName(), this.user_name) && Objects.equals(user.getEmail(), this.email);
	}
}
