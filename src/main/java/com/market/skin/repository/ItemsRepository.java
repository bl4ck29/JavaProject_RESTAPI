package com.market.skin.repository;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Items;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ItemsRepository extends JpaRepository<Items, Integer>{
    List<Items> findAll();

    @Query(value =  "select i from items i where i.gun_id = :gunId")
    List<Items> findByGunId(@Param("gunId") int gun_id);

    @Query(value =  "select i from items i where i.pattern_id = :pattId")
    List<Items> findByPatternId(@Param("pattId") int pattern_id);

    @Query(value =  "select i from items i where i.creator_id = :creId")
    List<Items> findByCreatorId(@Param("creId") int creator_id);
}
