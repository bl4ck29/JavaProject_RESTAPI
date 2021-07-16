package com.market.skin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

    @NotBlank @Size(min=3, max=15) @Column(name = "role_name")
    private String roleName;

    @OneToMany(targetEntity=Users.class, mappedBy="role",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    protected List<Users> users = new ArrayList<>();

    public Roles(){}
    public Roles(int id, String name){
        this.role_id = id;
        this.roleName = name;
    }
    public void setId(int id){this.role_id = id;}
    public void setRoleName(String name){this.roleName = name;}

    public int getId(){return this.role_id;}
    public String getRoleName(){return this.roleName;}
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