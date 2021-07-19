package com.market.skin.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "roles")
public class Roles{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private RolesName name;

    // @OneToMany(targetEntity=Users.class, mappedBy="user_role",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    // protected List<Users> users = new ArrayList<>();

    public Roles(){}
    public Roles(int id, RolesName name){
        this.role_id = id;
        this.name = name;
    }
    public void setId(int id){this.role_id = id;}
    public void setRoleName(RolesName name){this.name = name;}

    public int getId(){return this.role_id;}
    public RolesName getRoleName(){return this.name;}
    // public List<Users> getUsers(){return this.users;}

    @Override
    public boolean equals(Object other){
        if((other==null) || (other.getClass() != this.getClass())){
            return false;
        }
        Roles role = (Roles) other;
        return Objects.equals(role.getId(), this.role_id);
    }
}