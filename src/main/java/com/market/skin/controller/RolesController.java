package com.market.skin.controller;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Roles;
import com.market.skin.repository.RolesRepository;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RolesController {
    private final RolesRepository repository;

    public RolesController(RolesRepository repository){
        this.repository = repository;
    }

    @GetMapping("/roles")
    List<Roles> findAll(){return repository.findAll();}

    @GetMapping("/roles/{id}")
    Optional<Roles> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("roles/{id}")
    Roles modifyRole(@RequestBody Roles newRole, @PathVariable int id){
        return repository.findById(id).map(role -> {
            role.setRoleName(newRole.getRoleName());
            return repository.save(role);
        }).orElseGet(() -> {
            newRole.setId(id);
            return repository.save(newRole);
        });
    }
    @DeleteMapping("/roles/{id}")
    void deleteById(@PathVariable int id){
        repository.deleteById(id);
    }
}