package com.market.skin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.skin.service.GunsService;
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

    @GetMapping("/guns/find")
    ResponseEntity<?> findByGunName(@RequestParam(required=false, name = "attr") String attr ,@RequestParam(required=false, name = "value") String value)
    {
        if("name".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByGunName(value));
        } 
        if("typeid".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByTypeId(Integer.parseInt(value)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No gun matched");
    }

    @PostMapping("/guns")
    ResponseEntity<Guns> createGun(@RequestBody Guns newGun){
        Guns gun = new Guns();
        gun.setId(newGun.getId());
        gun.setTypeId(newGun.getTypeId());
        gun.setGunName(newGun.getGunName().strip());
        service.createGuns(gun);
        return ResponseEntity.status(HttpStatus.OK).body(gun);
    }

    @PutMapping("/guns")
    void modifyDetails(@RequestBody Guns other){
    
    }
    
    @DeleteMapping("/guns/{id}")
    ResponseEntity<Optional<Guns>> deleteGun(@PathVariable int id){
        Optional<Guns> res = service.deleteGun(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/guns/all/{page}")
    Page<Guns> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/guns/all/sort/{page}")
    Page<Guns> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") String asc){
        Boolean sort = Boolean.parseBoolean(asc);
        return service.sortByAttr(attr, page, sort);
    }
}
