package com.market.skin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Patterns;
import com.market.skin.repository.PatternsRepository;
@RestController
public class PatternsController {
    private final PatternsRepository repository;

    public PatternsController(PatternsRepository repository){
        this.repository = repository;
    }

    @GetMapping("/patterns")
    List<Patterns> findAll(){return repository.findAll();}

    @GetMapping("/patterns/{id}")
    Optional<Patterns> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("/patterns/{id}")
    Patterns modifyItem(@RequestBody Patterns newPatt, @PathVariable int id){
        return repository.findById(id).map( patt-> {
            patt.setName(newPatt.getName());
            return repository.save(patt);
        }).orElseGet(() -> {
            newPatt.setId(id);
            return repository.save(newPatt);
        });
    }

    @DeleteMapping("/patterns/{id}")
    void deletePattern(@PathVariable int id){repository.deleteById(id);}
}
