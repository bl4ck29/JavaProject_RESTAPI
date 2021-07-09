package com.market.skin.controller;

import java.util.Optional;

import com.market.skin.model.Transactions;
import com.market.skin.repository.TransRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransController {
    private final TransRepository repository;
    public TransController(TransRepository repository){
        this.repository = repository;
    }

    @GetMapping("/transactions/{id}")
    Optional<Transactions> findOne(@PathVariable int id){return repository.findById(id);}

    @PutMapping("/transactions/{id}")
    Transactions modifyTrans(@RequestBody Transactions newTrans, @PathVariable int id){
        return repository.findById(id).map( trans ->{
            trans.setId(id);
            trans.setItemId(newTrans.getItemId());
            trans.setStatus(trans.getStatus());
            trans.setUpdateTime(newTrans.getUpdateTime());
            return repository.save(trans);
        }).orElseGet(() ->{
            newTrans.setId(id);
            return repository.save(newTrans);
        });
    }

    @DeleteMapping("/transactions/{id}")
    void deleteTransaction(@PathVariable int id){repository.deleteById(id);}
}
