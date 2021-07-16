package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Transactions;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TransRepository extends JpaRepository<Transactions, Integer>{
    List<Transactions> findAll();

    @Query(value = "select t from transactions t where t.user_id = :userId")
    List<Transactions> findByUserId(@Param("userId") int user_id);
    
    @Query(value =  "select t from transactions t where t.item_id = :itemId")
    List<Transactions> findByItemId(@Param("itemId") int item_id);

    @Query(value =  "select t from transactions t where t.status = :status")
    List<Transactions> findByStatus(@Param("status") String status);
}
