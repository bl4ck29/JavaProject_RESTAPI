package com.market.skin.controller;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Roles;
import com.market.skin.service.RolesService;

import org.springframework.web.bind.annotation.RestController;
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

@RestController
public class RolesController {
    @Autowired
    private final RolesService service;

    public RolesController(RolesService service){
        this.service = service;
    }

    @GetMapping("/roles")
    ResponseEntity<Optional<Roles>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(service.findById(id));
    }

    @GetMapping("/roles/find")
    ResponseEntity<List<Roles>> findByRoleName(@RequestParam(required=false, name = "value") String value){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByRoleName(value));
    }

    @PutMapping("/roles")
    ResponseEntity<Roles> createGun(@RequestBody Roles newRole){
        service.createRole(newRole);
        return ResponseEntity.status(HttpStatus.OK).body(newRole);
    }
    
    @DeleteMapping("/roles/{id}")
    ResponseEntity<Optional<Roles>> deleteGun(@PathVariable int id){
        Optional<Roles> res = service.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/roles/all")
    Page<Roles> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/roles/all/sort")
    Page<Roles> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc){
        return service.sortByAttr(attr, page, asc);
    }
}