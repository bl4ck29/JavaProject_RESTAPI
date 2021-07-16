package com.market.skin.repository;

import com.market.skin.model.Users;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer>{
    List<Users> findAll();

    @Query(value =  "select u from users u where g.user_name = :userName")
    List<Users> findByUserName(@Param("userName") String user_name);

    @Query(value =  "select u from users u where u.role_id = :role_id")
    List<Users> findByRoleId(@Param("role_id") int role_id);

    @Query(value =  "select u from users u where u.email = :email")
    List<Users> findByEmail(@Param("email") String email);
}
