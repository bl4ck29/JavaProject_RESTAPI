package com.market.skin.repository;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Patterns;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PatternsRepository extends JpaRepository<Patterns, Integer>{
    List<Patterns> findAll();

    @Query(value =  "select p from patterns p where p.patterb_name = :name")
    Optional<Patterns> findByPatternName(@Param("name") String pattern_name);
}
