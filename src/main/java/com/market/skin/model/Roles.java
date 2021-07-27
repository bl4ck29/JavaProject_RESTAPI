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

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity @Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Roles{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private RolesName name;

    public Roles(int id, RolesName name){
        this.role_id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object other){
        if((other==null) || (other.getClass() != this.getClass())){
            return false;
        }
        Roles role = (Roles) other;
        return Objects.equals(role.getRole_id(), this.role_id);
    }
}