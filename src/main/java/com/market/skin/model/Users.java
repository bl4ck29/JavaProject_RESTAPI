package com.market.skin.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// import io.swagger.annotations.ApiModel;

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

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users", 
uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name", "email", "profile"})},
	indexes = @Index(columnList = "user_name", name = "name_ind"))
@Getter
@Setter
// @ApiModel(value = "Users Model")
public class Users {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)	@Column(name = "user_id")
	private int id;

    @NotBlank @Size(min = 4, max = 15) @Column(name = "user_name")
	private String userName;
	
    @NotBlank @Column(length = 3, name = "login_type")
	private String loginType;
    
    @Email @NotBlank @Size(max = 20)
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();

	@NotBlank() @Size(min = 10, max = 200)
    private String profile;

    @NotBlank @Size(min = 10, max = 100) @Column(name = "user_password")
	private String password;

	@OneToMany(targetEntity=Transactions.class, mappedBy="user")    
	protected List<Transactions> trans = new ArrayList<>();

	@OneToMany(targetEntity = Items.class, mappedBy = "cre_id")
	protected List<Items> lstItems = new ArrayList<>();

	public Users(){}
	public Users(String user_name, String login_type, String email, String profile, String user_password){
		this.userName = user_name;
		this.loginType = login_type;
		this.email = email;
		this.profile = profile;
		this.password = user_password;
	}
	
	@Override
	public boolean equals(Object other){
		if((other==null) || (other.getClass() != this.getClass())){
			return false;
		}
		Users user = (Users) other;
		return Objects.equals(user.getUserName(), this.userName) && Objects.equals(user.getEmail(), this.email);
	}
}
