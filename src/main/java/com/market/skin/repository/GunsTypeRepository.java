package com.market.skin.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.market.skin.model.GunsType;

public interface GunsTypeRepository extends JpaRepository<GunsType, Integer>{
    List<GunsType> findAll();

    Boolean existsByTypeName(String type_name);

    // @Query(value =  "select gt from guns_type gt where gt.type_name = :name")
    Optional<GunsType> findByTypeName(@Param("name") String type_name);
}
