package com.market.skin.repository;

import java.util.List;

import com.market.skin.model.Roles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    List<Roles> findAll();
}
