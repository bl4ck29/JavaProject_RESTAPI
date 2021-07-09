package com.market.skin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.skin.model.GunsType;

public interface GunsTypeRepository extends JpaRepository<GunsType, Integer>{
    List<GunsType> findAll();
}
