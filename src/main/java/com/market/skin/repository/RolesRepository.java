package com.market.skin.repository;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Roles;
import com.market.skin.model.RolesName;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    List<Roles> findAll();
    Optional<Roles> findByName(RolesName name);
}
