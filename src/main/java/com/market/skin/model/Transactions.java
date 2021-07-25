package com.market.skin.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Entity @Table(name = "transactions",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "item_id"})},
    indexes = @Index(columnList = "trans_id", name = "trans_ind"))
public class Transactions {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "trans_id")
    private int id;
    
    @NotNull @Column(name = "user_id")
    private int userId;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id", insertable = false, updatable = false)    
    private Users user;

    @NotNull @Column(name = "item_id")
    private int itemId;
    
    @ManyToMany(mappedBy = "likedTrans")
    private List<Items> likes;

    private LocalDateTime update_time;

    @NotBlank @Column(length = 3)
    private String status;


    public Transactions(){}
    public Transactions(int user_id, int item_id, LocalDateTime time, String stat){
        this.userId = user_id;
        this.itemId = item_id;
        this.update_time = time;
        this.status = stat;
    }

    public void setId(int id){this.id = id;}
    public void setUserId(int user_id){this.userId = user_id;}
    public void setItemId(int id){this.itemId = id;}
    public void setUpdateTime(LocalDateTime time){this.update_time = time;}
    public void setStatus(String stt){this.status = stt;}

    public int getId(){return this.id;}
    public int getUserId(){return this.userId;}
    public int getItemId(){return this.itemId;}
    public LocalDateTime getUpdateTime(){return this.update_time;}
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
