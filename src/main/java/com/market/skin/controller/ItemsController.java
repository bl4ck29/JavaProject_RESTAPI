package com.market.skin.controller;

import com.market.skin.service.ItemsService;
import com.market.skin.model.Items;

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

@RestController
public class ItemsController {
    private final ItemsService service;

    public ItemsController(ItemsService service){
        this.service = service;
    }

    @GetMapping("/items")
    ResponseEntity<Optional<Items>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.OK)
        .body(service.findById(id));
    }

    @GetMapping("/items/find")
    ResponseEntity<List<Items>> findByAttr(@RequestParam(required=false, name = "attr") String attr, @RequestParam(required=false, name = "value") String value){
        if("gunid".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByGunId(Integer.parseInt(value)));
        } else if("paternid".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByPatternId(Integer.parseInt(value)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/items")
    ResponseEntity<Items> createItems(@RequestBody Items newItem){
        service.create(newItem);
        return ResponseEntity.status(HttpStatus.OK).body(newItem);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<Optional<Items>> deleteItem(@PathVariable int id){
        Optional<Items> res = service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/items/all/{page}")
    Page<Items> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/items/all/sort")
    Page<Items> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc){
        return service.sortByAttr(attr, page, asc);
    }
}
