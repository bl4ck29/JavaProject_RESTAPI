package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransRepository extends JpaRepository<Transactions, Integer>{
    List<Transactions> findAll();
}
