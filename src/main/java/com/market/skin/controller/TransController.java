package com.market.skin.controller;

import java.util.Optional;

import com.market.skin.model.Transactions;
import com.market.skin.service.TransService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransController {
    @Autowired
    private final TransService service;

    public TransController(TransService service){
        this.service = service;
    }

    @GetMapping("/transactions")
    ResponseEntity<Optional<Transactions>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(service.findById(id));
    }

    @PutMapping("/transactions")
    ResponseEntity<Transactions> createGun(@RequestBody Transactions newTrans){
        service.create(newTrans);
        return ResponseEntity.status(HttpStatus.OK).body(newTrans);
    }
    
    @DeleteMapping("/transactions/{id}")
    ResponseEntity<Optional<Transactions>> deleteGun(@PathVariable int id){
        Optional<Transactions> res = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/guns/all")
    Page<Transactions> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/guns/all/sort")
    Page<Transactions> sort(@PathVariable Boolean asc, @PathVariable int page, @PathVariable String attr){
        return service.sortByAttr(attr, page, asc);
    }
}