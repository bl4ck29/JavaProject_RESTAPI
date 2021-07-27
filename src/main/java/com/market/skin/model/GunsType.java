package com.market.skin.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guns_type",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"type_name"})},
    indexes= {@Index(columnList ="type_name", name="type_name_ind")})

@Getter
@Setter
public class GunsType {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id")
    private int id;

    @NotBlank @Size(min = 5, max = 20) @Column(name = "type_name")
    private String typeName;

    @OneToMany(targetEntity=Guns.class, mappedBy="gunsType",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    protected List<Guns> guns = new ArrayList<>();
    
    public GunsType(){}
    public GunsType(String type_name){
        this.typeName = type_name;
    }

    @Override
    public boolean equals(Object other){
        if((other == null) || other.getClass() != this.getClass()){
            return false;
        } 
        GunsType gun = (GunsType) other;
        return Objects.equals(gun.getTypeName(), this.typeName);
    }
}
