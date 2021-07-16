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

@Entity @Table(name = "patterns")
public class Patterns {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int pattern_id;
    
    @NotBlank @Size(min=2, max=30) @Column(name = "pattern_name")
    private String patternName;

    @OneToMany(targetEntity = Items.class, mappedBy = "patterns", cascade = CascadeType.ALL, fetch = FetchType.LAZY)    
    private List<Items> items = new ArrayList<>();

    public Patterns(){}
    public Patterns(int id, String name){
        this.pattern_id = id;
        this.patternName = name;
    }

    public void setId(int id){this.pattern_id = id;}
    public void setName(String name){this.patternName = name;}

    public int getId(){return this.pattern_id;}
    public String getName(){return this.patternName;}
    public List<Items> getItems(){return this.items;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Patterns patt = (Patterns) other;
        return Objects.equals(patt.getName(), this.patternName);
    }
}
