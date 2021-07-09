package com.market.skin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.market.skin.model.Guns;
public interface GunsRepository extends JpaRepository<Guns, Integer>{
    List<Guns> findAll();
}
