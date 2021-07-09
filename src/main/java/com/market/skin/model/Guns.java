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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = "guns")
public class Guns {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int gun_id;

    private int type_id;

    @NotBlank @Size(max=20)
    private String gun_name;

    @ManyToOne()
    @JoinColumn(name="type_id", referencedColumnName = "type_id", insertable = false, updatable = false)    
    private GunsType gunsType;

    @OneToMany(targetEntity=Items.class, mappedBy="guns",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    private List<Items> user = new ArrayList<>();

    public Guns(){}
    public Guns(int id, int type_id, String gun_name){
        this.gun_id = id;
        this.type_id = type_id;
        this.gun_name = gun_name;
    }

    public void setId(int id){this.gun_id = id;}
    public void setTypeId(int id){this.type_id = id;}
    public void setGunName(String name){this.gun_name = name;}

    public int getId(){return this.gun_id;}
    public int getTypeId(){return this.type_id;}
    public String getGunName(){return this.gun_name;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Guns gun = (Guns) other;
        return Objects.equals(gun.getGunName(), this.gun_name) && Objects.equals(gun.getTypeId(), this.type_id);
    }
}
