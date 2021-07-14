package com.market.skin.controller;

import java.util.Optional;

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

import com.market.skin.service.GunsService;
import com.market.skin.exception.GunExistedById;
import com.market.skin.model.Guns;

@RestController
public class GunsController {
    @Autowired
    private final GunsService service;

    public GunsController(GunsService service){
        this.service = service;
    }

    @GetMapping("/guns")
    ResponseEntity<Optional<Guns>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(service.findById(id));
    }

    @PutMapping("/guns")
    ResponseEntity<Guns> createGun(@RequestBody Guns newGun) throws GunExistedById{
        service.createGuns(newGun);
        return ResponseEntity.status(HttpStatus.OK).body(newGun);
    }
    
    @DeleteMapping("/guns/{id}")
    ResponseEntity<Optional<Guns>> deleteGun(@PathVariable int id){
        Optional<Guns> res = service.deleteGun(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/guns/all")
    Page<Guns> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/guns/all/sort")
    Page<Guns> sort(@PathVariable Boolean asc, @PathVariable int page, @PathVariable String attr){
        return service.sortByAttr(attr, page, asc);
    }
}
