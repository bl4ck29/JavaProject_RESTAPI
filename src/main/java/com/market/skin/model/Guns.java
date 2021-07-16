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

    @Column(name = "type_id")
    private int typeId;

    @NotBlank @Size(max=20)
    @Column(name = "gun_name")
    private String gunName;

    @ManyToOne()
    @JoinColumn(name="type_id", referencedColumnName = "type_id", insertable = false, updatable = false)    
    private GunsType gunsType;

    @OneToMany(targetEntity=Items.class, mappedBy="guns",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    protected List<Items> items = new ArrayList<>();

    public Guns(){}
    public Guns(int id, int type_id, String gun_name){
        this.gun_id = id;
        this.typeId = type_id;
        this.gunName = gun_name;
    }

    public void setId(int id){this.gun_id = id;}
    public void setTypeId(int id){this.typeId = id;}
    public void setGunName(String name){this.gunName = name;}

    public int getId(){return this.gun_id;}
    public int getTypeId(){return this.typeId;}
    public String getGunName(){return this.gunName;}
    public List<Items> getItems(){return this.items;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Guns gun = (Guns) other;
        return Objects.equals(gun.getGunName(), this.gunName) && Objects.equals(gun.getTypeId(), this.typeId);
    }
}