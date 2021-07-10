package com.market.skin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.market.skin.repository.GunsTypeRepository;
import com.market.skin.model.GunsType;

import java.util.List;
import java.util.Optional;

@RestController
public class GunsTypeController {
    private final GunsTypeRepository repository;

    public GunsTypeController(GunsTypeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/types")
    List<GunsType> findAll(){return repository.findAll();}

    @GetMapping("/types/{id}")
    Optional<GunsType> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("types/{id}")
    GunsType modifyGunsType(@RequestBody GunsType newType, @PathVariable int id){
        return repository.findById(id).map(gunType -> {
            gunType.setGunName(newType.getName());
            return repository.save(gunType);
        }).orElseGet(() -> {
            newType.setId(id);
            return repository.save(newType);
        });
    }

    @DeleteMapping("/types/{id}")
    void deleteGunsType(@PathVariable int id){repository.deleteById(id);}
}
