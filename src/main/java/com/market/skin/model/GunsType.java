package com.market.skin.model;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Table(name = "guns_type")
public class GunsType {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int type_id;

    @NotBlank @Size(min = 5, max = 20)
    private String type_name;

    @OneToMany(targetEntity=Guns.class, mappedBy="gunsType",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    protected List<Guns> guns = new ArrayList<>();
    
    public GunsType(){}
    public GunsType(int id, String name){
        this.type_id = id;
        this.type_name = name;
    }

    public void setId(int id){this.type_id = id;}
    public void setGunName(String name){this.type_name = name;}

    public int getId(){return this.type_id;}
    public String getName(){return this.type_name;}

    @Override
    public boolean equals(Object other){
        if((other == null) || other.getClass() != this.getClass()){
            return false;
        } 
        GunsType gun = (GunsType) other;
        return Objects.equals(gun.getName(), this.type_name);
    }
}
