package com.market.skin.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.market.skin.model.Guns;
public interface GunsRepository extends JpaRepository<Guns, Integer>{
    List<Guns> findAll();

    @Query(value =  "select g from guns g where g.type_id = :type_id")
    List<Guns> findByTypeId(int type_id);

    @Query(value =  "select g from guns g where g.gun_name = :name")
    List<Guns> findByGunName(@Param("name") String gun_name);
}
