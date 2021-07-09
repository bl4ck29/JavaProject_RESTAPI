package com.market.skin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = "roles")
public class Roles{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    @NotBlank @Size(min=3, max=15)
    private String role_name;

    @OneToMany(targetEntity=Users.class, mappedBy="role",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    private List<Users> users = new ArrayList<>();

    public Roles(){}
    public Roles(int id, String name){
        this.role_id = id;
        this.role_name = name;
    }
    public void setId(int id){this.role_id = id;}
    public void setRoleName(String name){this.role_name = name;}

    public int getId(){return this.role_id;}
    public String getRoleName(){return this.role_name;}
    public List<Users> getUsers(){return this.users;}

    @Override
    public boolean equals(Object other){
        if((other==null) || (other.getClass() != this.getClass())){
            return false;
        }
        Roles role = (Roles) other;
        return Objects.equals(role.getId(), this.role_id);
    }
}