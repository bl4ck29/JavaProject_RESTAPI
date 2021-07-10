package com.market.skin.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = "patterns")
public class Patterns {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int pattern_id;
    
    @NotBlank @Size(min=2, max=30)
    private String pattern_name;

    @ManyToOne()
    @JoinColumn(name="pattern_id", referencedColumnName = "pattern_id", insertable = false, updatable = false)    
    private Patterns patterns;

    public Patterns(){}
    public Patterns(int id, String name){
        this.pattern_id = id;
        this.pattern_name = name;
    }

    public void setId(int id){this.pattern_id = id;}
    public void setName(String name){this.pattern_name = name;}

    public int getId(){return this.pattern_id;}
    public String getName(){return this.pattern_name;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Patterns patt = (Patterns) other;
        return Objects.equals(patt.getName(), this.pattern_name);
    }
}
