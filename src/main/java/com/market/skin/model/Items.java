package com.market.skin.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// import io.swagger.annotations.ApiModel;
import lombok.Setter;
import lombok.Getter;

@Entity @Table(name = "items",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"pattern_id", "gun_id"})},
    indexes = @Index(columnList = "item_id", name = "item_ind"))
@Setter
@Getter
// @ApiModel(value = "Items Model")
public class Items {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int item_id;

    @NotNull @Column(name = "gun_id")
    private int gunId;

    @ManyToOne()
    @JoinColumn(name="gun_id", referencedColumnName = "gun_id", insertable = false, updatable = false)    
    private Guns guns;

    @NotNull @Column(name = "pattern_id")
    private int patternId;

    @NotNull @Column(name = "creator_id")
    private int creatorId;

    @ManyToOne()
    @JoinColumn(name ="creator_id", referencedColumnName= "user_id", insertable = false, updatable = false)
    protected Users cre_id;

    @Size(min = 10, max = 200) 
    private String item_image;

    @NotNull
    private float price;
    
    @ManyToOne()
    @JoinColumn(name="pattern_id", referencedColumnName = "pattern_id", insertable = false, updatable = false)
    protected Patterns patterns;

    @ManyToMany
    @JoinTable(name = "trans_items", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "trans_id"))
    private List<Transactions> likedTrans;

    public Items(){}
    public Items(int gun_id, int patt_id, int cre_id, String image, float price){
        this.gunId = gun_id;
        this.patternId = patt_id;
        this.creatorId = cre_id;
        this.item_image = image;
        this.price = price;
    }

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Items item = (Items) other;
        return (item.getGunId() == this.gunId) && (item.getPatternId() == this.patternId);
    }
}
