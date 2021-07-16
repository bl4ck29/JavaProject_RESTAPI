package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Roles;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    List<Roles> findAll();

    @Query(value =  "select r from roles r where r.role_name = :name")
    List<Roles> findByRoleName(@Param("name") String role_name);
}
