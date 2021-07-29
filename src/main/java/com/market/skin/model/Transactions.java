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

// import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity @Table(name = "transactions",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "item_id"})},
    indexes = {
        @Index(columnList="item_id", name="trans_item_ind"),
        @Index(columnList = "trans_id, user_id", name = "trans_ind"),
        @Index(columnList ="user_id", name = "trans_user_ind")})
@Getter
@Setter
@NoArgsConstructor
// @ApiModel(value = "Transactions Model")
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

    public Transactions(int user_id, int item_id, LocalDateTime time, String stat){
        this.userId = user_id;
        this.itemId = item_id;
        this.update_time = time;
        this.status = stat;
    }

    @Override
    public boolean equals(Object other){
        if((other == null) || (other.getClass() != this.getClass())){
            return false;
        }
        Transactions cart = (Transactions) other;
        return Objects.equals(cart.getItemId(), this.itemId) && (cart.getId() == this.userId) && Objects.equals(cart.getStatus(), this.status);
    }
    
}
