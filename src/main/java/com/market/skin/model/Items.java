package com.market.skin.model;

import java.util.ArrayList;
import java.util.List;

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
import javax.validation.constraints.Size;

@Entity @Table(name = "items")
public class Items {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int item_id;

    private int gun_id;
    @ManyToOne()
    @JoinColumn(name="gun_id", referencedColumnName = "gun_id", insertable = false, updatable = false)    
    private Guns guns;

    private int pattern_id;
    
    private int creator_id;
    @Size(min = 10, max = 200)
    private String item_image;
    
    @OneToMany(targetEntity=Patterns.class, mappedBy="patterns",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
    protected List<Patterns> patterns = new ArrayList<>();


    public Items(){}
    public Items(int id, int gun_id, int patt_id, int cre_id, String image){
        this.item_id = id;
        this.gun_id = gun_id;
        this.pattern_id = patt_id;
        this.creator_id = cre_id;
        this.item_image = image;
    }

    public void setId(int id){this.item_id = id;}
    public void setGunId(int id){this.gun_id = id;}
    public void setPatternId(int id){this.pattern_id = id;}
    public void setCreatorId(int id){this.creator_id = id;}
    public void setImage(String image){this.item_image = image;}

    public int getId(){return this.item_id;}
    public int getGunId(){return this.gun_id;}
    public int getPatternId(){return this.pattern_id;}
    public int getCreatorId(){return this.creator_id;}
    public String getImage(){return this.item_image;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Items item = (Items) other;
        return (item.getGunId() == this.gun_id) && (item.getPatternId() == this.pattern_id);
    }
}
