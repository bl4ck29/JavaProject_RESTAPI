
package com.market.skin.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity @Table(name = "transactions")
public class Transactions {
    @Id
    private int trans_id;
    
    private int user_id;
    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "user_id", insertable = false, updatable = false)    
    private Users user;

    @NotBlank
    private int item_id;
    @NotBlank
    private Timestamp update_time;
    @NotBlank @Column(length = 3)
    private String status;


    public Transactions(){}
    public Transactions(int id, int item_id, Timestamp time, String stat){
        this.user_id = id;
        this.item_id = item_id;
        this.update_time = time;
        this.status = stat;
    }

    public void setId(int id){this.user_id = id;}
    public void setItemId(int id){this.item_id = id;}
    public void setUpdateTime(Timestamp time){this.update_time = time;}
    public void setStatus(String stt){this.status = stt;}

    public int getId(){return this.user_id;}
    public int getItemId(){return this.item_id;}
    public Timestamp getUpdateTime(){return this.update_time;}
    public String getStatus(){return this.status;}

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Transactions cart = (Transactions) other;
        return Objects.equals(cart.getItemId(), this.item_id) && (cart.getId() == this.user_id) && Objects.equals(cart.getStatus(), this.status);
    }
    
}
