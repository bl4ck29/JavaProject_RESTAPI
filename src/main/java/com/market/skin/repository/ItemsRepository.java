package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Items;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer>{
    List<Items> findAll();
}
