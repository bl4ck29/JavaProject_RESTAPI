package com.market.skin.controller;

import java.util.List;
import java.util.Optional;

import com.market.skin.repository.UsersRepository;
import com.market.skin.model.Users;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UsersController {
    private final UsersRepository repository;

    public UsersController(UsersRepository repository){
        this.repository = repository;
    }

    @GetMapping("/users")
    List<Users> findAll(){return repository.findAll();}

    @GetMapping("/users/{id}")
    Optional<Users> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("/users/{id}")
    Users modifyUser(@RequestBody Users newUser, @PathVariable int id){
        return repository.findById(id).map(user -> {
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setRoleId(newUser.getRoleId());
            user.setLoginType(newUser.getLoginType());
            user.setProfile(newUser.getProfile());
            user.setPassword(newUser.getPassword());
            return repository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
    }

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable int id) {repository.deleteById(id);}
}
