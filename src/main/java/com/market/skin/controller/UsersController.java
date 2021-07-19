package com.market.skin.controller;

import java.util.Optional;

import com.market.skin.service.UsersService;
import com.market.skin.model.Users;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class UsersController {
    private final UsersService service;

    public UsersController(UsersService service){
        this.service = service;
    }

    @GetMapping("/users")
    ResponseEntity<Optional<Users>> findOne(@RequestParam(required=false, name = "id") int id){
        return ResponseEntity.status(HttpStatus.OK)
        .body(
            service.findById(id)
        );
    }

    @PutMapping("/users")
    void createUser(@RequestBody Users newUser){
        service.createUser(newUser);
    }
    
    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted done");
    }

    @GetMapping("/users/all/{page}")
    Page<Users> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/users/all/sort/{page}")
    Page<Users> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc){
        return service.sortByAttr(attr, page, asc);
    }
}
