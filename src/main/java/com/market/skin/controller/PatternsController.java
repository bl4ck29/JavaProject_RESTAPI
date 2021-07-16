package com.market.skin.controller;

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

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Patterns;
import com.market.skin.service.PatternsService;
@RestController
public class PatternsController {
    @Autowired
    private PatternsService service;

    public PatternsController(PatternsService service){
        this.service = service;
    }

    @GetMapping("/patterns")
    ResponseEntity<Optional<Patterns>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(service.findById(id));
    }

    @GetMapping("/patterns/find")
    ResponseEntity<List<Patterns>> findByPatternName(@RequestParam(required=false, name = "value") String value){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByPatternName(value));
    }

    @PutMapping("/patterns")
    ResponseEntity<Patterns> createGun(@RequestBody Patterns newPatt){
        service.create(newPatt);
        return ResponseEntity.status(HttpStatus.OK).body(newPatt);
    }
    
    @DeleteMapping("/patterns/{id}")
    ResponseEntity<Optional<Patterns>> deleteGun(@PathVariable int id){
        Optional<Patterns> res = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/pattern/all")
    Page<Patterns> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/patterns/all/sort")
    Page<Patterns> sort(@PathVariable Boolean asc, @PathVariable int page, @PathVariable String attr){
        return service.sortByAttr(attr, page, asc);
    }
}
