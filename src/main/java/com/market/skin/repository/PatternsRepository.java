package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Patterns;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatternsRepository extends JpaRepository<Patterns, Integer>{
    List<Patterns> findAll();
}
