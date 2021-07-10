package com.market.skin.controller;

import com.market.skin.repository.ItemsRepository;
import com.market.skin.model.Items;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemsController {
    private final ItemsRepository repository;

    public ItemsController(ItemsRepository repository){
        this.repository = repository;
    }

    @GetMapping("/items")
    List<Items> findAll(){return repository.findAll();}
    
    @GetMapping("/items/{id}")
    Optional<Items> findOne(@PathVariable int id){
        return repository.findById(id);
    }

    @PutMapping("/items/{id}")
    Items modifyItem(@RequestBody Items newItem, @PathVariable int id){
        return repository.findById(id).map(item ->{
            item.setCreatorId(newItem.getCreatorId());
            item.setGunId(newItem.getGunId());
            item.setImage(newItem.getImage());
            item.setPatternId(newItem.getPatternId());
            return repository.save(item);
        }).orElseGet(() ->{
            newItem.setId(id);
            return repository.save(newItem);
        });
    }
}
