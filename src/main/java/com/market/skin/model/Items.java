package com.market.skin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = "items")
public class Items {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int item_id;

    @NotBlank @Column(name = "gun_id")
    private int gunId;

    @ManyToOne()
    @JoinColumn(name="gun_id", referencedColumnName = "gun_id", insertable = false, updatable = false)    
    private Guns guns;

    @NotBlank @Column(name = "pattern_id")
    private int patternId;

    @NotBlank @Column(name = "creator_id")
    private int creatorId;

    @Size(min = 10, max = 200) 
    private String item_image;
    
    @ManyToOne()
    @JoinColumn(name="pattern_id", referencedColumnName = "pattern_id", insertable = false, updatable = false)
    protected Patterns patterns;

    @ManyToMany
    @JoinTable(name = "trans_items", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "trans_id"))
    private List<Transactions> likedTrans;

    public Items(){}
    public Items(int id, int gun_id, int patt_id, int cre_id, String image){
        this.item_id = id;
        this.gunId = gun_id;
        this.patternId = patt_id;
        this.creatorId = cre_id;
        this.item_image = image;
    }

    public void setId(int id){this.item_id = id;}
    public void setGunId(int id){this.gunId = id;}
    public void setPatternId(int id){this.patternId = id;}
    public void setCreatorId(int id){this.creatorId = id;}
    public void setImage(String image){this.item_image = image;}

    public int getId(){return this.item_id;}
    public int getGunId(){return this.gunId;}
    public int getPatternId(){return this.patternId;}
    public int getCreatorId(){return this.creatorId;}
    public String getImage(){return this.item_image;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Items item = (Items) other;
        return (item.getGunId() == this.gunId) && (item.getPatternId() == this.patternId);
    }
}
