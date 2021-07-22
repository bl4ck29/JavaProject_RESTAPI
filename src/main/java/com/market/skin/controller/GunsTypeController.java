package com.market.skin.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.skin.service.GunsTypeService;
import com.market.skin.model.GunsType;

@RestController
public class GunsTypeController {
    private final GunsTypeService service;

    public GunsTypeController(GunsTypeService service){
        this.service = service;
    }

    @GetMapping("/types")
    ResponseEntity<Optional<GunsType>> findOne(@RequestParam(required=false, name = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping("/types/find")
    ResponseEntity<Optional<GunsType>> findByTypeName(@RequestParam(required=false, name = "value") String type_name){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByTypeName(type_name));
    }

    @PostMapping("/types")
    ResponseEntity<GunsType> createGunType(@RequestBody GunsType newType){
        service.createGuns(newType);
        return ResponseEntity.status(HttpStatus.OK).body(newType);
    }
    
    @DeleteMapping("/types/{id}")
    ResponseEntity<Optional<GunsType>> deleteType(@PathVariable int id){
        Optional<GunsType> res = service.deleteGun(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
