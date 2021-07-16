package com.market.skin.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity @Table(name = "transactions")
public class Transactions {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int trans_id;
    
    @NotBlank @Column(name = "user_id")
    private int userId;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id", insertable = false, updatable = false)    
    private Users user;

    @NotBlank @Column(name = "item_id")
    private int itemId;
    
    @ManyToMany(mappedBy = "likedTrans")
    private List<Items> likes;

    @NotBlank
    private Timestamp update_time;

    @NotBlank @Column(length = 3)
    private String status;


    public Transactions(){}
    public Transactions(int id, int item_id, Timestamp time, String stat){
        this.trans_id = id;
        this.userId = id;
        this.itemId = item_id;
        this.update_time = time;
        this.status = stat;
    }

    public void setId(int id){this.userId = id;}
    public void setItemId(int id){this.itemId = id;}
    public void setUpdateTime(Timestamp time){this.update_time = time;}
    public void setStatus(String stt){this.status = stt;}

    public int getId(){return this.userId;}
    public int getItemId(){return this.itemId;}
    public Timestamp getUpdateTime(){return this.update_time;}
    public String getStatus(){return this.status;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Transactions cart = (Transactions) other;
        return Objects.equals(cart.getItemId(), this.itemId) && (cart.getId() == this.userId) && Objects.equals(cart.getStatus(), this.status);
    }
    
}
