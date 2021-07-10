package com.market.skin.repository;

import com.market.skin.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer>{
    List<Users> findAll();

}
