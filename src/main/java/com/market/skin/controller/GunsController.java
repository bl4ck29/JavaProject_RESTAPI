package com.market.skin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.market.skin.repository.GunsRepository;
import com.market.skin.model.Guns;

@RestController
public class GunsController {
    private final GunsRepository repository;

    public GunsController(GunsRepository repository){
        this.repository = repository;
    }

    @GetMapping("/guns")
    List<Guns> findAll(){return repository.findAll();}

    @GetMapping("/guns/{id}")
    Optional<Guns> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("guns/{id}")
    Guns modifyGun(@RequestBody Guns newGun, @PathVariable int id){
        return repository.findById(id).map(gun ->{
            gun.setGunName(newGun.getGunName());
            gun.setTypeId(newGun.getTypeId());
            return repository.save(gun);
        }).orElseGet(()->{
            newGun.setId(id);
            return repository.save(newGun);
        });
    }

    @DeleteMapping("/guns/{id}")
    void deleteGun(@PathVariable int id){repository.deleteById(id);}
}
