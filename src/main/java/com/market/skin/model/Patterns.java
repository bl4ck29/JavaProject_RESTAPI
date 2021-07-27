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
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "patterns", indexes={@Index(columnList="pattern_name", name="pattern_name_ind")})
@Getter
@Setter
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

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Patterns patt = (Patterns) other;
        return Objects.equals(patt.getPatternName(), this.patternName);
    }
}
