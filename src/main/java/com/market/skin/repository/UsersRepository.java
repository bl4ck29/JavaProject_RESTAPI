package com.market.skin.repository;

import com.market.skin.model.Users;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer>{
    List<Users> findAll();

    Optional<Users> findById(Long id);

    Boolean existsByUserName(String name);

    Boolean existsByEmail(String email);

    @Query(value =  "select u from users u where g.user_name = :userName")
    Optional<Users> findByUserName(@Param("userName") String user_name);

    @Query(value =  "select u from users u where u.email = :email")
    List<Users> findByEmail(@Param("email") String email);
}
